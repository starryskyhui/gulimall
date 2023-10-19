package com.atguigu.gulimall.gulimallproduct.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.atguigu.gulimall.gulimallproduct.service.CategoryBrandRelationService;
import com.atguigu.gulimall.gulimallproduct.vo.Catalog2Vo;
import org.apache.commons.lang.StringUtils;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.atguigu.common.utils.PageUtils;
import com.atguigu.common.utils.Query;

import com.atguigu.gulimall.gulimallproduct.dao.CategoryDao;
import com.atguigu.gulimall.gulimallproduct.entity.CategoryEntity;
import com.atguigu.gulimall.gulimallproduct.service.CategoryService;
import org.springframework.transaction.annotation.Transactional;


/**
 * @author 郑国辉
 */
@Service("categoryService")
public class CategoryServiceImpl extends ServiceImpl<CategoryDao, CategoryEntity> implements CategoryService {

    @Autowired
    CategoryBrandRelationService categoryBrandRelationService;

    @Autowired
    StringRedisTemplate stringRedisTemplate;

    @Autowired
    RedissonClient redisson;

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<CategoryEntity> page = this.page(
                new Query<CategoryEntity>().getPage(params),
                new QueryWrapper<CategoryEntity>()
        );

        return new PageUtils(page);
    }

    @Override
    public List<CategoryEntity> listWithTree() {
        List<CategoryEntity> categoryEntities = this.baseMapper.selectList(null);

        List<CategoryEntity> level1Menus = categoryEntities.stream()
                .filter(categoryEntity -> categoryEntity.getParentCid() == 0)
                .map((categoryEntity) -> {
                    categoryEntity.setChildren(getChildren(categoryEntity, categoryEntities));
                    return categoryEntity;
                })
                .sorted((menu1, menu2) -> (menu1.getSort()==null?0:menu1.getSort())-(menu2.getSort()==null?0:menu2.getSort()))
                .collect(Collectors.toList());

        return level1Menus;
    }

    @Override
    public void removeMenuByIds(List<Long> asList) {
        //TODO 1、检查当前删除的菜单，是否被别的地方引用
        baseMapper.deleteBatchIds(asList);
    }

    @Override
    public Long[] findCatelogPath(Long catelogId) {
        List<Long> paths = new ArrayList<>();
        findPatentPath(catelogId, paths);
        Collections.reverse(paths);
        return paths.toArray(new Long[0]);
    }

    /**
     * @CacheEvict：失效模式
     * @param category
     */
    @Caching(evict = {
            @CacheEvict(value = "category", key = "'getLevel1Categorys'"),
            @CacheEvict(value = "category", key = "'getCatalogJson'")
    })
    @Transactional
    @Override
    public void updateCascade(CategoryEntity category) {
        updateById(category);
        categoryBrandRelationService.updateCategory(category.getCatId(), category.getName());
    }

    @Cacheable(value = {"category"}, key = "#root.method.name")
    @Override
    public List<CategoryEntity> getLevel1Categorys() {
        List<CategoryEntity> categoryEntities = this.baseMapper.selectList(new QueryWrapper<CategoryEntity>().eq("parent_cid", 0));

        return categoryEntities;
    }

    @Cacheable(value = "category", key = "#root.methodName")
    @Override
    public Map<String, List<Catalog2Vo>>  getCatalogJson() {
        // 1.查出所有1级分类
        List<CategoryEntity> level1Categorys = getLevel1Categorys();

        List<CategoryEntity> entityList = baseMapper.selectList(new QueryWrapper<CategoryEntity>(null));

        // 2.封装数据
        Map<String, List<Catalog2Vo>> res = level1Categorys.stream().collect(Collectors.toMap(k->k.getCatId().toString(), v->{
            List<CategoryEntity> categoryEntities = entityList.stream().filter(e->v.getCatId().equals(e.getParentCid())).collect(Collectors.toList());
            List<Catalog2Vo> catalog2Vos = null;
            if (categoryEntities != null) {
                catalog2Vos = categoryEntities.stream().map(l2 -> {
                    Catalog2Vo catalog2Vo = new Catalog2Vo(v.getCatId().toString(), null, l2.getCatId().toString(), l2.getName());
                    // List<CategoryEntity> entities = baseMapper.selectList(new QueryWrapper<CategoryEntity>().eq("parent_cid", l2.getCatId()));
                    List<CategoryEntity> entities = entityList.stream().filter(e->l2.getCatId().equals(e.getParentCid())).collect(Collectors.toList());
                    if (entities != null) {
                        List<Catalog2Vo.Catalog3Vo> catalog3Vos = entities.stream().map(l3 -> {
                            Catalog2Vo.Catalog3Vo catalog3Vo = new Catalog2Vo.Catalog3Vo(l2.getCatId().toString(), l3.getCatId().toString(), l3.getName());
                            return catalog3Vo;
                        }).collect(Collectors.toList());
                        catalog2Vo.setCatalog3List(catalog3Vos);
                    }
                    return catalog2Vo;
                }).collect(Collectors.toList());
            }
            return catalog2Vos;
        }));
        return res;
    }

    // TODO 产生堆外内存溢出：outOfDirectMemoryError
    // 1)、SpringBoot2.0以后默认使用lettuce作为操作redis的客户端。它使用netty进行网络通信
    // 2）、lettuce的bug导致netty堆外内存溢出 ；netty如果没有指定堆外内存，默认使用-Xmx
    //      通过-Dio.netty.maxDirectMemory进行设置
    // 解决方案：不能使用-Dio.netty.maxDirectMemory只去调大堆外内存
    // 1）、升级lettuce客户端。 2）、切换使用jedis
    public Map<String, List<Catalog2Vo>>  getCatalogJson2(){
        // 给缓存中放入json字符串，拿出的json字符串，还用逆转为能用的对象类型；【序列化与反序列化】

        /**
         * 1、空结果缓存：解决缓存穿透
         * 2、设置过期时间（加随机值）：解决缓存雪崩
         * 3、加锁：解决缓存击穿
         */
        // 1、加入缓存逻辑，缓存中存的数据是json字符串。
        // json跨语言，跨平台
        String catalogJSON = stringRedisTemplate.opsForValue().get("catalogJSON");
        if (StringUtils.isEmpty(catalogJSON)) {
            // 2、缓存中没有，查询数据库
            Map<String, List<Catalog2Vo>> catalogJsonFromDb = getCatalogJsonFromDbWithRedissonLock();
            return catalogJsonFromDb;
        }

        // 反序列化
        Map<String, List<Catalog2Vo>> result = JSON.parseObject(catalogJSON, new TypeReference<Map<String, List<Catalog2Vo>>>() {
        });
        return result;
    }
    public Map<String, List<Catalog2Vo>>  getCatalogJsonFromDbWithRedissonLock() {

        // 1、锁的名字，锁的粒度，越细越快
        RLock lock = redisson.getLock("CatalogJson-lock");
        lock.lock();
        Map<String,List<Catalog2Vo>> dataFromDb;
        try {
            dataFromDb = getCatalogJsonFromDb();
        } finally {
            lock.unlock();
        }

        return dataFromDb;
    }

    public Map<String, List<Catalog2Vo>> getCatalogJsonFromDb() {

        synchronized (this) {
            // DCL
            String catalogJSON = stringRedisTemplate.opsForValue().get("catalogJSON");
            if (!StringUtils.isEmpty(catalogJSON)) {
                Map<String, List<Catalog2Vo>> result = JSON.parseObject(catalogJSON, new TypeReference<Map<String, List<Catalog2Vo>>>() {
                });
                return result;
            }
            // 1.查出所有1级分类
            List<CategoryEntity> level1Categorys = getLevel1Categorys();

            List<CategoryEntity> entityList = baseMapper.selectList(new QueryWrapper<CategoryEntity>(null));

            // 2.封装数据
            Map<String, List<Catalog2Vo>> res = level1Categorys.stream().collect(Collectors.toMap(k->k.getCatId().toString(), v->{
                List<CategoryEntity> categoryEntities = entityList.stream().filter(e->v.getCatId().equals(e.getParentCid())).collect(Collectors.toList());
                List<Catalog2Vo> catalog2Vos = null;
                if (categoryEntities != null) {
                    catalog2Vos = categoryEntities.stream().map(l2 -> {
                        Catalog2Vo catalog2Vo = new Catalog2Vo(v.getCatId().toString(), null, l2.getCatId().toString(), l2.getName());
                        // List<CategoryEntity> entities = baseMapper.selectList(new QueryWrapper<CategoryEntity>().eq("parent_cid", l2.getCatId()));
                        List<CategoryEntity> entities = entityList.stream().filter(e->l2.getCatId().equals(e.getParentCid())).collect(Collectors.toList());
                        if (entities != null) {
                            List<Catalog2Vo.Catalog3Vo> catalog3Vos = entities.stream().map(l3 -> {
                                Catalog2Vo.Catalog3Vo catalog3Vo = new Catalog2Vo.Catalog3Vo(l2.getCatId().toString(), l3.getCatId().toString(), l3.getName());
                                return catalog3Vo;
                            }).collect(Collectors.toList());
                            catalog2Vo.setCatalog3List(catalog3Vos);
                        }
                        return catalog2Vo;
                    }).collect(Collectors.toList());
                }
                return catalog2Vos;
            }));

            // 3、查到的数据再放入缓存，将对象转化为JSON放入缓存中
            String s = JSON.toJSONString(res);
            stringRedisTemplate.opsForValue().set("catalogJSON", s, 1, TimeUnit.DAYS);
            return res;
        }
    }

    private void findPatentPath(Long catelogId, List<Long> paths) {
        // 1. 收集当前节点的id
        paths.add(catelogId);
        CategoryEntity byId = this.getById(catelogId);
        if (byId.getParentCid() != 0) {
            findPatentPath(byId.getParentCid(), paths);
        }
    }

    private List<CategoryEntity> getChildren(CategoryEntity entity, List<CategoryEntity> all) {
        List<CategoryEntity> children = all.stream()
                .filter(categoryEntity -> categoryEntity.getParentCid().equals(entity.getCatId()))
                .map((categoryEntity) -> {
                    categoryEntity.setChildren(getChildren(categoryEntity, all));
                    return categoryEntity;
                })
                .sorted((menu1, menu2) -> (menu1.getSort()==null?0:menu1.getSort())-(menu2.getSort()==null?0:menu2.getSort()))
                .collect(Collectors.toList());
        return children;
    }

}