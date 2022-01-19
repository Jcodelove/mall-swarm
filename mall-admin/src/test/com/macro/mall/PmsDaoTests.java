package com.macro.mall;


import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONUtil;
import com.github.pagehelper.PageHelper;
import com.macro.mall.common.api.CommonResult;
import com.macro.mall.dao.PmsMemberPriceDao;
import com.macro.mall.dao.PmsProductDao;
import com.macro.mall.dto.PmsProductParam;
import com.macro.mall.dto.PmsProductResult;
import com.macro.mall.mapper.PmsProductMapper;
import com.macro.mall.model.PmsMemberPrice;
import com.macro.mall.model.PmsProduct;
import com.macro.mall.model.PmsProductExample;
import com.macro.mall.service.PmsProductService;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.collections.functors.FalsePredicate;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class PmsDaoTests {
    @Resource
    private PmsProductMapper productMapper;
    @Autowired
    private PmsProductService productService;
    @Resource
    private PmsMemberPriceDao memberPriceDao;
    @Resource
    private PmsProductDao productDao;
    private static final Logger LOGGER = LoggerFactory.getLogger(PmsDaoTests.class);
    @Test
    @Transactional
    @Rollback
    public void testInsertBatch(){
        List<PmsMemberPrice> list = new ArrayList<>();
        for(int i=0;i<5;i++){
            PmsMemberPrice memberPrice = new PmsMemberPrice();
            memberPrice.setProductId(1L);
            memberPrice.setMemberLevelId((long) (i+1));
            memberPrice.setMemberPrice(new BigDecimal("22"));
            list.add(memberPrice);
        }
        int count = memberPriceDao.insertList(list);
        Assert.assertEquals(5,count);
    }

    @Test
    public void  testGetProductUpdateInfo(){
        PmsProductResult productResult = productDao.getUpdateInfo(7L);
        String json = JSONUtil.parse(productResult).toString();
        LOGGER.info(json);
    }

    @Test
    public void testProductsearch() {
        String keyword = null; Long brandId = null; Long productCategoryId = null; Integer pageNum = 1; Integer pageSize = 5; Integer sort = 0;
        PageHelper.startPage(pageNum, pageSize);
        PmsProductExample example = new PmsProductExample();
        PmsProductExample.Criteria criteria = example.createCriteria();
        criteria.andDeleteStatusEqualTo(0);
        if (StrUtil.isNotEmpty(keyword)) {
            criteria.andNameLike("%" + keyword + "%");
        }
        if (brandId != null) {
            criteria.andBrandIdEqualTo(brandId);
        }
        if (productCategoryId != null) {
            criteria.andProductCategoryIdEqualTo(productCategoryId);
        }
        //1->按新品；2->按销量；3->价格从低到高；4->价格从高到低
        if (sort == 1) {
            example.setOrderByClause("id desc");
        } else if (sort == 2) {
            example.setOrderByClause("sale desc");
        } else if (sort == 3) {
            example.setOrderByClause("price asc");
        } else if (sort == 4) {
            example.setOrderByClause("price desc");
        }
        List<PmsProduct> pmsProducts = productMapper.selectByExample(example);
        System.out.println(pmsProducts);
        for (PmsProduct pmsProduct : pmsProducts) {
            System.out.println(pmsProduct);
        }
    }

    @Test
    public void testCreateProduct( ) {
        String str = "{\"id\":80000,\"brandId\":6,\"productCategoryId\":19,\"feightTemplateId\":0,\"productAttributeCategoryId\":3,\"name\":\"小米75120 全面屏游戏智能手机 6GB+64GB 黑色 全网通4G 双卡双待\",\"pic\":\"http://macro-oss.oss-cn-shenzhen.aliyuncs.com/mall/images/20180615/xiaomi.jpg\",\"productSn\":\"8000001\",\"deleteStatus\":0,\"publishStatus\":1,\"newStatus\":1,\"recommandStatus\":1,\"verifyStatus\":0,\"sort\":0,\"sale\":0,\"price\":3699,\"promotionPrice\":null,\"giftGrowth\":3699,\"giftPoint\":3699,\"usePointLimit\":0,\"subTitle\":\"小米75120 全面屏游戏智能手机 6GB+64GB 黑色 全网通4G 双卡双待\",\"originalPrice\":3699,\"stock\":100,\"lowStock\":0,\"unit\":\"\",\"weight\":200,\"previewStatus\":0,\"serviceIds\":\"\",\"keywords\":\"\",\"note\":\"\",\"albumPics\":\"\",\"detailTitle\":\"\",\"promotionStartTime\":null,\"promotionEndTime\":null,\"promotionPerLimit\":0,\"promotionType\":3,\"brandName\":\"小米\",\"productCategoryName\":\"手机通讯\",\"description\":\"小米75120 全面屏游戏智能手机 6GB+64GB 黑色 全网通4G 双卡双待\",\"detailDesc\":\"\",\"detailHtml\":\"<p><img class=\\\"wscnph\\\" src=\\\"http://macro-oss.oss-cn-shenzhen.aliyuncs.com/mall/images/20180615/5b2254e8N414e6d3a.jpg\\\" width=\\\"500\\\" /></p>\",\"detailMobileHtml\":\"\",\"productLadderList\":[{\"id\":87,\"productId\":38,\"count\":2,\"discount\":0.8,\"price\":0},{\"id\":88,\"productId\":38,\"count\":3,\"discount\":0.75,\"price\":0}],\"productFullReductionList\":[{\"id\":81,\"productId\":38,\"fullPrice\":0,\"reducePrice\":0}],\"memberPriceList\":[],\"skuStockList\":[{\"id\":183,\"productId\":38,\"skuCode\":\"2022011301\",\"price\":3698,\"stock\":100,\"lowStock\":null,\"pic\":null,\"sale\":null,\"promotionPrice\":null,\"lockStock\":null,\"spData\":\"[{\\\"key\\\":\\\"颜色\\\",\\\"value\\\":\\\"粉色\\\"},{\\\"key\\\":\\\"容量\\\",\\\"value\\\":\\\"64G\\\"}]\"},{\"id\":184,\"productId\":38,\"skuCode\":\"2022011302\",\"price\":3698,\"stock\":100,\"lowStock\":null,\"pic\":null,\"sale\":null,\"promotionPrice\":null,\"lockStock\":null,\"spData\":\"[{\\\"key\\\":\\\"颜色\\\",\\\"value\\\":\\\"粉色\\\"},{\\\"key\\\":\\\"容量\\\",\\\"value\\\":\\\"128G\\\"}]\"},{\"id\":185,\"productId\":38,\"skuCode\":\"2022011303\",\"price\":3698,\"stock\":100,\"lowStock\":null,\"pic\":null,\"sale\":null,\"promotionPrice\":null,\"lockStock\":null,\"spData\":\"[{\\\"key\\\":\\\"颜色\\\",\\\"value\\\":\\\"黑色\\\"},{\\\"key\\\":\\\"容量\\\",\\\"value\\\":\\\"64G\\\"}]\"},{\"id\":186,\"productId\":38,\"skuCode\":\"2022011304\",\"price\":3698,\"stock\":100,\"lowStock\":null,\"pic\":null,\"sale\":null,\"promotionPrice\":null,\"lockStock\":null,\"spData\":\"[{\\\"key\\\":\\\"颜色\\\",\\\"value\\\":\\\"黑色\\\"},{\\\"key\\\":\\\"容量\\\",\\\"value\\\":\\\"128G\\\"}]\"}],\"productAttributeValueList\":[{\"productAttributeId\":43,\"value\":\"黑色,粉色\"},{\"productAttributeId\":45,\"value\":\"6.3\"},{\"productAttributeId\":46,\"value\":\"4G\"},{\"productAttributeId\":47,\"value\":\"Android\"},{\"productAttributeId\":48,\"value\":\"5000\"}],\"subjectProductRelationList\":[],\"prefrenceAreaProductRelationList\":[{\"id\":26,\"prefrenceAreaId\":1,\"productId\":38}],\"cateParentId\":2}";
        str.replaceAll("80000", "95000");
        PmsProductParam productParam = JSONUtil.toBean(str, PmsProductParam.class);
        
        productParam.setId(95000L);
        int flag = productService.create(productParam);
        
    }


    @Test
    public void testCreateProduct2( ) {
        

        new Thread(() -> {
            String str = "{\"id\":80000,\"brandId\":6,\"productCategoryId\":19,\"feightTemplateId\":0,\"productAttributeCategoryId\":3,\"name\":\"小米75120 全面屏游戏智能手机 6GB+64GB 黑色 全网通4G 双卡双待\",\"pic\":\"http://macro-oss.oss-cn-shenzhen.aliyuncs.com/mall/images/20180615/xiaomi.jpg\",\"productSn\":\"8000001\",\"deleteStatus\":0,\"publishStatus\":1,\"newStatus\":1,\"recommandStatus\":1,\"verifyStatus\":0,\"sort\":0,\"sale\":0,\"price\":3699,\"promotionPrice\":null,\"giftGrowth\":3699,\"giftPoint\":3699,\"usePointLimit\":0,\"subTitle\":\"小米75120 全面屏游戏智能手机 6GB+64GB 黑色 全网通4G 双卡双待\",\"originalPrice\":3699,\"stock\":100,\"lowStock\":0,\"unit\":\"\",\"weight\":200,\"previewStatus\":0,\"serviceIds\":\"\",\"keywords\":\"\",\"note\":\"\",\"albumPics\":\"\",\"detailTitle\":\"\",\"promotionStartTime\":null,\"promotionEndTime\":null,\"promotionPerLimit\":0,\"promotionType\":3,\"brandName\":\"小米\",\"productCategoryName\":\"手机通讯\",\"description\":\"小米75120 全面屏游戏智能手机 6GB+64GB 黑色 全网通4G 双卡双待\",\"detailDesc\":\"\",\"detailHtml\":\"<p><img class=\\\"wscnph\\\" src=\\\"http://macro-oss.oss-cn-shenzhen.aliyuncs.com/mall/images/20180615/5b2254e8N414e6d3a.jpg\\\" width=\\\"500\\\" /></p>\",\"detailMobileHtml\":\"\",\"productLadderList\":[{\"id\":87,\"productId\":38,\"count\":2,\"discount\":0.8,\"price\":0},{\"id\":88,\"productId\":38,\"count\":3,\"discount\":0.75,\"price\":0}],\"productFullReductionList\":[{\"id\":81,\"productId\":38,\"fullPrice\":0,\"reducePrice\":0}],\"memberPriceList\":[],\"skuStockList\":[{\"id\":183,\"productId\":38,\"skuCode\":\"2022011301\",\"price\":3698,\"stock\":100,\"lowStock\":null,\"pic\":null,\"sale\":null,\"promotionPrice\":null,\"lockStock\":null,\"spData\":\"[{\\\"key\\\":\\\"颜色\\\",\\\"value\\\":\\\"粉色\\\"},{\\\"key\\\":\\\"容量\\\",\\\"value\\\":\\\"64G\\\"}]\"},{\"id\":184,\"productId\":38,\"skuCode\":\"2022011302\",\"price\":3698,\"stock\":100,\"lowStock\":null,\"pic\":null,\"sale\":null,\"promotionPrice\":null,\"lockStock\":null,\"spData\":\"[{\\\"key\\\":\\\"颜色\\\",\\\"value\\\":\\\"粉色\\\"},{\\\"key\\\":\\\"容量\\\",\\\"value\\\":\\\"128G\\\"}]\"},{\"id\":185,\"productId\":38,\"skuCode\":\"2022011303\",\"price\":3698,\"stock\":100,\"lowStock\":null,\"pic\":null,\"sale\":null,\"promotionPrice\":null,\"lockStock\":null,\"spData\":\"[{\\\"key\\\":\\\"颜色\\\",\\\"value\\\":\\\"黑色\\\"},{\\\"key\\\":\\\"容量\\\",\\\"value\\\":\\\"64G\\\"}]\"},{\"id\":186,\"productId\":38,\"skuCode\":\"2022011304\",\"price\":3698,\"stock\":100,\"lowStock\":null,\"pic\":null,\"sale\":null,\"promotionPrice\":null,\"lockStock\":null,\"spData\":\"[{\\\"key\\\":\\\"颜色\\\",\\\"value\\\":\\\"黑色\\\"},{\\\"key\\\":\\\"容量\\\",\\\"value\\\":\\\"128G\\\"}]\"}],\"productAttributeValueList\":[{\"productAttributeId\":43,\"value\":\"黑色,粉色\"},{\"productAttributeId\":45,\"value\":\"6.3\"},{\"productAttributeId\":46,\"value\":\"4G\"},{\"productAttributeId\":47,\"value\":\"Android\"},{\"productAttributeId\":48,\"value\":\"5000\"}],\"subjectProductRelationList\":[],\"prefrenceAreaProductRelationList\":[{\"id\":26,\"prefrenceAreaId\":1,\"productId\":38}],\"cateParentId\":2}";
            str.replaceAll("80000", "77236");
            PmsProductParam productParam = JSONUtil.toBean(str, PmsProductParam.class);
            for (long i = 77237; i < 80000; i++) {

                productParam.setId(i);
                productParam.setName(productParam.getName().replace("小米" + (i - 1),"小米" + i));
                productService.create(productParam);
            }
        }).start();


        new Thread(() -> {
            String str = "{\"id\":80000,\"brandId\":6,\"productCategoryId\":19,\"feightTemplateId\":0,\"productAttributeCategoryId\":3,\"name\":\"小米75120 全面屏游戏智能手机 6GB+64GB 黑色 全网通4G 双卡双待\",\"pic\":\"http://macro-oss.oss-cn-shenzhen.aliyuncs.com/mall/images/20180615/xiaomi.jpg\",\"productSn\":\"8000001\",\"deleteStatus\":0,\"publishStatus\":1,\"newStatus\":1,\"recommandStatus\":1,\"verifyStatus\":0,\"sort\":0,\"sale\":0,\"price\":3699,\"promotionPrice\":null,\"giftGrowth\":3699,\"giftPoint\":3699,\"usePointLimit\":0,\"subTitle\":\"小米75120 全面屏游戏智能手机 6GB+64GB 黑色 全网通4G 双卡双待\",\"originalPrice\":3699,\"stock\":100,\"lowStock\":0,\"unit\":\"\",\"weight\":200,\"previewStatus\":0,\"serviceIds\":\"\",\"keywords\":\"\",\"note\":\"\",\"albumPics\":\"\",\"detailTitle\":\"\",\"promotionStartTime\":null,\"promotionEndTime\":null,\"promotionPerLimit\":0,\"promotionType\":3,\"brandName\":\"小米\",\"productCategoryName\":\"手机通讯\",\"description\":\"小米75120 全面屏游戏智能手机 6GB+64GB 黑色 全网通4G 双卡双待\",\"detailDesc\":\"\",\"detailHtml\":\"<p><img class=\\\"wscnph\\\" src=\\\"http://macro-oss.oss-cn-shenzhen.aliyuncs.com/mall/images/20180615/5b2254e8N414e6d3a.jpg\\\" width=\\\"500\\\" /></p>\",\"detailMobileHtml\":\"\",\"productLadderList\":[{\"id\":87,\"productId\":38,\"count\":2,\"discount\":0.8,\"price\":0},{\"id\":88,\"productId\":38,\"count\":3,\"discount\":0.75,\"price\":0}],\"productFullReductionList\":[{\"id\":81,\"productId\":38,\"fullPrice\":0,\"reducePrice\":0}],\"memberPriceList\":[],\"skuStockList\":[{\"id\":183,\"productId\":38,\"skuCode\":\"2022011301\",\"price\":3698,\"stock\":100,\"lowStock\":null,\"pic\":null,\"sale\":null,\"promotionPrice\":null,\"lockStock\":null,\"spData\":\"[{\\\"key\\\":\\\"颜色\\\",\\\"value\\\":\\\"粉色\\\"},{\\\"key\\\":\\\"容量\\\",\\\"value\\\":\\\"64G\\\"}]\"},{\"id\":184,\"productId\":38,\"skuCode\":\"2022011302\",\"price\":3698,\"stock\":100,\"lowStock\":null,\"pic\":null,\"sale\":null,\"promotionPrice\":null,\"lockStock\":null,\"spData\":\"[{\\\"key\\\":\\\"颜色\\\",\\\"value\\\":\\\"粉色\\\"},{\\\"key\\\":\\\"容量\\\",\\\"value\\\":\\\"128G\\\"}]\"},{\"id\":185,\"productId\":38,\"skuCode\":\"2022011303\",\"price\":3698,\"stock\":100,\"lowStock\":null,\"pic\":null,\"sale\":null,\"promotionPrice\":null,\"lockStock\":null,\"spData\":\"[{\\\"key\\\":\\\"颜色\\\",\\\"value\\\":\\\"黑色\\\"},{\\\"key\\\":\\\"容量\\\",\\\"value\\\":\\\"64G\\\"}]\"},{\"id\":186,\"productId\":38,\"skuCode\":\"2022011304\",\"price\":3698,\"stock\":100,\"lowStock\":null,\"pic\":null,\"sale\":null,\"promotionPrice\":null,\"lockStock\":null,\"spData\":\"[{\\\"key\\\":\\\"颜色\\\",\\\"value\\\":\\\"黑色\\\"},{\\\"key\\\":\\\"容量\\\",\\\"value\\\":\\\"128G\\\"}]\"}],\"productAttributeValueList\":[{\"productAttributeId\":43,\"value\":\"黑色,粉色\"},{\"productAttributeId\":45,\"value\":\"6.3\"},{\"productAttributeId\":46,\"value\":\"4G\"},{\"productAttributeId\":47,\"value\":\"Android\"},{\"productAttributeId\":48,\"value\":\"5000\"}],\"subjectProductRelationList\":[],\"prefrenceAreaProductRelationList\":[{\"id\":26,\"prefrenceAreaId\":1,\"productId\":38}],\"cateParentId\":2}";
            str.replaceAll("80000", "85000");
            PmsProductParam productParam = JSONUtil.toBean(str, PmsProductParam.class);
            for (long i = 80001; i < 85000; i++) {

                productParam.setId(i);
                productParam.setName(productParam.getName().replace("小米" + (i - 1),"小米" + i));
                productService.create(productParam);
            }
        }).start();

        new Thread(() -> {
            String str = "{\"id\":85000,\"brandId\":6,\"productCategoryId\":19,\"feightTemplateId\":0,\"productAttributeCategoryId\":3,\"name\":\"小米75120 全面屏游戏智能手机 6GB+64GB 黑色 全网通4G 双卡双待\",\"pic\":\"http://macro-oss.oss-cn-shenzhen.aliyuncs.com/mall/images/20180615/xiaomi.jpg\",\"productSn\":\"8500001\",\"deleteStatus\":0,\"publishStatus\":1,\"newStatus\":1,\"recommandStatus\":1,\"verifyStatus\":0,\"sort\":0,\"sale\":0,\"price\":3699,\"promotionPrice\":null,\"giftGrowth\":3699,\"giftPoint\":3699,\"usePointLimit\":0,\"subTitle\":\"小米75120 全面屏游戏智能手机 6GB+64GB 黑色 全网通4G 双卡双待\",\"originalPrice\":3699,\"stock\":100,\"lowStock\":0,\"unit\":\"\",\"weight\":200,\"previewStatus\":0,\"serviceIds\":\"\",\"keywords\":\"\",\"note\":\"\",\"albumPics\":\"\",\"detailTitle\":\"\",\"promotionStartTime\":null,\"promotionEndTime\":null,\"promotionPerLimit\":0,\"promotionType\":3,\"brandName\":\"小米\",\"productCategoryName\":\"手机通讯\",\"description\":\"小米75120 全面屏游戏智能手机 6GB+64GB 黑色 全网通4G 双卡双待\",\"detailDesc\":\"\",\"detailHtml\":\"<p><img class=\\\"wscnph\\\" src=\\\"http://macro-oss.oss-cn-shenzhen.aliyuncs.com/mall/images/20180615/5b2254e8N414e6d3a.jpg\\\" width=\\\"500\\\" /></p>\",\"detailMobileHtml\":\"\",\"productLadderList\":[{\"id\":87,\"productId\":38,\"count\":2,\"discount\":0.8,\"price\":0},{\"id\":88,\"productId\":38,\"count\":3,\"discount\":0.75,\"price\":0}],\"productFullReductionList\":[{\"id\":81,\"productId\":38,\"fullPrice\":0,\"reducePrice\":0}],\"memberPriceList\":[],\"skuStockList\":[{\"id\":183,\"productId\":38,\"skuCode\":\"2022011301\",\"price\":3698,\"stock\":100,\"lowStock\":null,\"pic\":null,\"sale\":null,\"promotionPrice\":null,\"lockStock\":null,\"spData\":\"[{\\\"key\\\":\\\"颜色\\\",\\\"value\\\":\\\"粉色\\\"},{\\\"key\\\":\\\"容量\\\",\\\"value\\\":\\\"64G\\\"}]\"},{\"id\":184,\"productId\":38,\"skuCode\":\"2022011302\",\"price\":3698,\"stock\":100,\"lowStock\":null,\"pic\":null,\"sale\":null,\"promotionPrice\":null,\"lockStock\":null,\"spData\":\"[{\\\"key\\\":\\\"颜色\\\",\\\"value\\\":\\\"粉色\\\"},{\\\"key\\\":\\\"容量\\\",\\\"value\\\":\\\"128G\\\"}]\"},{\"id\":185,\"productId\":38,\"skuCode\":\"2022011303\",\"price\":3698,\"stock\":100,\"lowStock\":null,\"pic\":null,\"sale\":null,\"promotionPrice\":null,\"lockStock\":null,\"spData\":\"[{\\\"key\\\":\\\"颜色\\\",\\\"value\\\":\\\"黑色\\\"},{\\\"key\\\":\\\"容量\\\",\\\"value\\\":\\\"64G\\\"}]\"},{\"id\":186,\"productId\":38,\"skuCode\":\"2022011304\",\"price\":3698,\"stock\":100,\"lowStock\":null,\"pic\":null,\"sale\":null,\"promotionPrice\":null,\"lockStock\":null,\"spData\":\"[{\\\"key\\\":\\\"颜色\\\",\\\"value\\\":\\\"黑色\\\"},{\\\"key\\\":\\\"容量\\\",\\\"value\\\":\\\"128G\\\"}]\"}],\"productAttributeValueList\":[{\"productAttributeId\":43,\"value\":\"黑色,粉色\"},{\"productAttributeId\":45,\"value\":\"6.3\"},{\"productAttributeId\":46,\"value\":\"4G\"},{\"productAttributeId\":47,\"value\":\"Android\"},{\"productAttributeId\":48,\"value\":\"5000\"}],\"subjectProductRelationList\":[],\"prefrenceAreaProductRelationList\":[{\"id\":26,\"prefrenceAreaId\":1,\"productId\":38}],\"cateParentId\":2}";
            str.replaceAll("85000", "90000");
            PmsProductParam productParam = JSONUtil.toBean(str, PmsProductParam.class);
            for (long i = 85001; i < 90000; i++) {

                productParam.setId(i);
                productParam.setName(productParam.getName().replace("小米" + (i - 1),"小米" + i));
                productService.create(productParam);
            }
        }).start();

        new Thread(() -> {
            String str = "{\"id\":85000,\"brandId\":6,\"productCategoryId\":19,\"feightTemplateId\":0,\"productAttributeCategoryId\":3,\"name\":\"小米75120 全面屏游戏智能手机 6GB+64GB 黑色 全网通4G 双卡双待\",\"pic\":\"http://macro-oss.oss-cn-shenzhen.aliyuncs.com/mall/images/20180615/xiaomi.jpg\",\"productSn\":\"8500001\",\"deleteStatus\":0,\"publishStatus\":1,\"newStatus\":1,\"recommandStatus\":1,\"verifyStatus\":0,\"sort\":0,\"sale\":0,\"price\":3699,\"promotionPrice\":null,\"giftGrowth\":3699,\"giftPoint\":3699,\"usePointLimit\":0,\"subTitle\":\"小米75120 全面屏游戏智能手机 6GB+64GB 黑色 全网通4G 双卡双待\",\"originalPrice\":3699,\"stock\":100,\"lowStock\":0,\"unit\":\"\",\"weight\":200,\"previewStatus\":0,\"serviceIds\":\"\",\"keywords\":\"\",\"note\":\"\",\"albumPics\":\"\",\"detailTitle\":\"\",\"promotionStartTime\":null,\"promotionEndTime\":null,\"promotionPerLimit\":0,\"promotionType\":3,\"brandName\":\"小米\",\"productCategoryName\":\"手机通讯\",\"description\":\"小米75120 全面屏游戏智能手机 6GB+64GB 黑色 全网通4G 双卡双待\",\"detailDesc\":\"\",\"detailHtml\":\"<p><img class=\\\"wscnph\\\" src=\\\"http://macro-oss.oss-cn-shenzhen.aliyuncs.com/mall/images/20180615/5b2254e8N414e6d3a.jpg\\\" width=\\\"500\\\" /></p>\",\"detailMobileHtml\":\"\",\"productLadderList\":[{\"id\":87,\"productId\":38,\"count\":2,\"discount\":0.8,\"price\":0},{\"id\":88,\"productId\":38,\"count\":3,\"discount\":0.75,\"price\":0}],\"productFullReductionList\":[{\"id\":81,\"productId\":38,\"fullPrice\":0,\"reducePrice\":0}],\"memberPriceList\":[],\"skuStockList\":[{\"id\":183,\"productId\":38,\"skuCode\":\"2022011301\",\"price\":3698,\"stock\":100,\"lowStock\":null,\"pic\":null,\"sale\":null,\"promotionPrice\":null,\"lockStock\":null,\"spData\":\"[{\\\"key\\\":\\\"颜色\\\",\\\"value\\\":\\\"粉色\\\"},{\\\"key\\\":\\\"容量\\\",\\\"value\\\":\\\"64G\\\"}]\"},{\"id\":184,\"productId\":38,\"skuCode\":\"2022011302\",\"price\":3698,\"stock\":100,\"lowStock\":null,\"pic\":null,\"sale\":null,\"promotionPrice\":null,\"lockStock\":null,\"spData\":\"[{\\\"key\\\":\\\"颜色\\\",\\\"value\\\":\\\"粉色\\\"},{\\\"key\\\":\\\"容量\\\",\\\"value\\\":\\\"128G\\\"}]\"},{\"id\":185,\"productId\":38,\"skuCode\":\"2022011303\",\"price\":3698,\"stock\":100,\"lowStock\":null,\"pic\":null,\"sale\":null,\"promotionPrice\":null,\"lockStock\":null,\"spData\":\"[{\\\"key\\\":\\\"颜色\\\",\\\"value\\\":\\\"黑色\\\"},{\\\"key\\\":\\\"容量\\\",\\\"value\\\":\\\"64G\\\"}]\"},{\"id\":186,\"productId\":38,\"skuCode\":\"2022011304\",\"price\":3698,\"stock\":100,\"lowStock\":null,\"pic\":null,\"sale\":null,\"promotionPrice\":null,\"lockStock\":null,\"spData\":\"[{\\\"key\\\":\\\"颜色\\\",\\\"value\\\":\\\"黑色\\\"},{\\\"key\\\":\\\"容量\\\",\\\"value\\\":\\\"128G\\\"}]\"}],\"productAttributeValueList\":[{\"productAttributeId\":43,\"value\":\"黑色,粉色\"},{\"productAttributeId\":45,\"value\":\"6.3\"},{\"productAttributeId\":46,\"value\":\"4G\"},{\"productAttributeId\":47,\"value\":\"Android\"},{\"productAttributeId\":48,\"value\":\"5000\"}],\"subjectProductRelationList\":[],\"prefrenceAreaProductRelationList\":[{\"id\":26,\"prefrenceAreaId\":1,\"productId\":38}],\"cateParentId\":2}";
            str.replaceAll("85000", "90000");
            PmsProductParam productParam = JSONUtil.toBean(str, PmsProductParam.class);
            for (long i = 90001; i < 95000; i++) {

                productParam.setId(i);
                productParam.setName(productParam.getName().replace("小米" + (i - 1),"小米" + i));
                productService.create(productParam);
            }
        }).start();

        new Thread(() -> {
            String str = "{\"id\":85000,\"brandId\":6,\"productCategoryId\":19,\"feightTemplateId\":0,\"productAttributeCategoryId\":3,\"name\":\"小米75120 全面屏游戏智能手机 6GB+64GB 黑色 全网通4G 双卡双待\",\"pic\":\"http://macro-oss.oss-cn-shenzhen.aliyuncs.com/mall/images/20180615/xiaomi.jpg\",\"productSn\":\"8500001\",\"deleteStatus\":0,\"publishStatus\":1,\"newStatus\":1,\"recommandStatus\":1,\"verifyStatus\":0,\"sort\":0,\"sale\":0,\"price\":3699,\"promotionPrice\":null,\"giftGrowth\":3699,\"giftPoint\":3699,\"usePointLimit\":0,\"subTitle\":\"小米75120 全面屏游戏智能手机 6GB+64GB 黑色 全网通4G 双卡双待\",\"originalPrice\":3699,\"stock\":100,\"lowStock\":0,\"unit\":\"\",\"weight\":200,\"previewStatus\":0,\"serviceIds\":\"\",\"keywords\":\"\",\"note\":\"\",\"albumPics\":\"\",\"detailTitle\":\"\",\"promotionStartTime\":null,\"promotionEndTime\":null,\"promotionPerLimit\":0,\"promotionType\":3,\"brandName\":\"小米\",\"productCategoryName\":\"手机通讯\",\"description\":\"小米75120 全面屏游戏智能手机 6GB+64GB 黑色 全网通4G 双卡双待\",\"detailDesc\":\"\",\"detailHtml\":\"<p><img class=\\\"wscnph\\\" src=\\\"http://macro-oss.oss-cn-shenzhen.aliyuncs.com/mall/images/20180615/5b2254e8N414e6d3a.jpg\\\" width=\\\"500\\\" /></p>\",\"detailMobileHtml\":\"\",\"productLadderList\":[{\"id\":87,\"productId\":38,\"count\":2,\"discount\":0.8,\"price\":0},{\"id\":88,\"productId\":38,\"count\":3,\"discount\":0.75,\"price\":0}],\"productFullReductionList\":[{\"id\":81,\"productId\":38,\"fullPrice\":0,\"reducePrice\":0}],\"memberPriceList\":[],\"skuStockList\":[{\"id\":183,\"productId\":38,\"skuCode\":\"2022011301\",\"price\":3698,\"stock\":100,\"lowStock\":null,\"pic\":null,\"sale\":null,\"promotionPrice\":null,\"lockStock\":null,\"spData\":\"[{\\\"key\\\":\\\"颜色\\\",\\\"value\\\":\\\"粉色\\\"},{\\\"key\\\":\\\"容量\\\",\\\"value\\\":\\\"64G\\\"}]\"},{\"id\":184,\"productId\":38,\"skuCode\":\"2022011302\",\"price\":3698,\"stock\":100,\"lowStock\":null,\"pic\":null,\"sale\":null,\"promotionPrice\":null,\"lockStock\":null,\"spData\":\"[{\\\"key\\\":\\\"颜色\\\",\\\"value\\\":\\\"粉色\\\"},{\\\"key\\\":\\\"容量\\\",\\\"value\\\":\\\"128G\\\"}]\"},{\"id\":185,\"productId\":38,\"skuCode\":\"2022011303\",\"price\":3698,\"stock\":100,\"lowStock\":null,\"pic\":null,\"sale\":null,\"promotionPrice\":null,\"lockStock\":null,\"spData\":\"[{\\\"key\\\":\\\"颜色\\\",\\\"value\\\":\\\"黑色\\\"},{\\\"key\\\":\\\"容量\\\",\\\"value\\\":\\\"64G\\\"}]\"},{\"id\":186,\"productId\":38,\"skuCode\":\"2022011304\",\"price\":3698,\"stock\":100,\"lowStock\":null,\"pic\":null,\"sale\":null,\"promotionPrice\":null,\"lockStock\":null,\"spData\":\"[{\\\"key\\\":\\\"颜色\\\",\\\"value\\\":\\\"黑色\\\"},{\\\"key\\\":\\\"容量\\\",\\\"value\\\":\\\"128G\\\"}]\"}],\"productAttributeValueList\":[{\"productAttributeId\":43,\"value\":\"黑色,粉色\"},{\"productAttributeId\":45,\"value\":\"6.3\"},{\"productAttributeId\":46,\"value\":\"4G\"},{\"productAttributeId\":47,\"value\":\"Android\"},{\"productAttributeId\":48,\"value\":\"5000\"}],\"subjectProductRelationList\":[],\"prefrenceAreaProductRelationList\":[{\"id\":26,\"prefrenceAreaId\":1,\"productId\":38}],\"cateParentId\":2}";
            str.replaceAll("85000", "90000");
            PmsProductParam productParam = JSONUtil.toBean(str, PmsProductParam.class);
            for (long i = 95001; i < 100000; i++) {
                productParam.setId(i);
                productParam.setName(productParam.getName().replace("小米" + (i - 1),"小米" + i));
                productService.create(productParam);
            }
        }).run();


    }

    @Test
    public void testCreateProduct3( ) {


        new Thread(() -> {
            String str = "{\"id\":85000,\"brandId\":6,\"productCategoryId\":19,\"feightTemplateId\":0,\"productAttributeCategoryId\":3,\"name\":\"小米75120 全面屏游戏智能手机 6GB+64GB 黑色 全网通4G 双卡双待\",\"pic\":\"http://macro-oss.oss-cn-shenzhen.aliyuncs.com/mall/images/20180615/xiaomi.jpg\",\"productSn\":\"8500001\",\"deleteStatus\":0,\"publishStatus\":1,\"newStatus\":1,\"recommandStatus\":1,\"verifyStatus\":0,\"sort\":0,\"sale\":0,\"price\":3699,\"promotionPrice\":null,\"giftGrowth\":3699,\"giftPoint\":3699,\"usePointLimit\":0,\"subTitle\":\"小米75120 全面屏游戏智能手机 6GB+64GB 黑色 全网通4G 双卡双待\",\"originalPrice\":3699,\"stock\":100,\"lowStock\":0,\"unit\":\"\",\"weight\":200,\"previewStatus\":0,\"serviceIds\":\"\",\"keywords\":\"\",\"note\":\"\",\"albumPics\":\"\",\"detailTitle\":\"\",\"promotionStartTime\":null,\"promotionEndTime\":null,\"promotionPerLimit\":0,\"promotionType\":3,\"brandName\":\"小米\",\"productCategoryName\":\"手机通讯\",\"description\":\"小米75120 全面屏游戏智能手机 6GB+64GB 黑色 全网通4G 双卡双待\",\"detailDesc\":\"\",\"detailHtml\":\"<p><img class=\\\"wscnph\\\" src=\\\"http://macro-oss.oss-cn-shenzhen.aliyuncs.com/mall/images/20180615/5b2254e8N414e6d3a.jpg\\\" width=\\\"500\\\" /></p>\",\"detailMobileHtml\":\"\",\"productLadderList\":[{\"id\":87,\"productId\":38,\"count\":2,\"discount\":0.8,\"price\":0},{\"id\":88,\"productId\":38,\"count\":3,\"discount\":0.75,\"price\":0}],\"productFullReductionList\":[{\"id\":81,\"productId\":38,\"fullPrice\":0,\"reducePrice\":0}],\"memberPriceList\":[],\"skuStockList\":[{\"id\":183,\"productId\":38,\"skuCode\":\"2022011301\",\"price\":3698,\"stock\":100,\"lowStock\":null,\"pic\":null,\"sale\":null,\"promotionPrice\":null,\"lockStock\":null,\"spData\":\"[{\\\"key\\\":\\\"颜色\\\",\\\"value\\\":\\\"粉色\\\"},{\\\"key\\\":\\\"容量\\\",\\\"value\\\":\\\"64G\\\"}]\"},{\"id\":184,\"productId\":38,\"skuCode\":\"2022011302\",\"price\":3698,\"stock\":100,\"lowStock\":null,\"pic\":null,\"sale\":null,\"promotionPrice\":null,\"lockStock\":null,\"spData\":\"[{\\\"key\\\":\\\"颜色\\\",\\\"value\\\":\\\"粉色\\\"},{\\\"key\\\":\\\"容量\\\",\\\"value\\\":\\\"128G\\\"}]\"},{\"id\":185,\"productId\":38,\"skuCode\":\"2022011303\",\"price\":3698,\"stock\":100,\"lowStock\":null,\"pic\":null,\"sale\":null,\"promotionPrice\":null,\"lockStock\":null,\"spData\":\"[{\\\"key\\\":\\\"颜色\\\",\\\"value\\\":\\\"黑色\\\"},{\\\"key\\\":\\\"容量\\\",\\\"value\\\":\\\"64G\\\"}]\"},{\"id\":186,\"productId\":38,\"skuCode\":\"2022011304\",\"price\":3698,\"stock\":100,\"lowStock\":null,\"pic\":null,\"sale\":null,\"promotionPrice\":null,\"lockStock\":null,\"spData\":\"[{\\\"key\\\":\\\"颜色\\\",\\\"value\\\":\\\"黑色\\\"},{\\\"key\\\":\\\"容量\\\",\\\"value\\\":\\\"128G\\\"}]\"}],\"productAttributeValueList\":[{\"productAttributeId\":43,\"value\":\"黑色,粉色\"},{\"productAttributeId\":45,\"value\":\"6.3\"},{\"productAttributeId\":46,\"value\":\"4G\"},{\"productAttributeId\":47,\"value\":\"Android\"},{\"productAttributeId\":48,\"value\":\"5000\"}],\"subjectProductRelationList\":[],\"prefrenceAreaProductRelationList\":[{\"id\":26,\"prefrenceAreaId\":1,\"productId\":38}],\"cateParentId\":2}";
            str.replaceAll("85000", "90000");
            PmsProductParam productParam = JSONUtil.toBean(str, PmsProductParam.class);
            for (long i = 100000; i < 105000; i++) {

                productParam.setId(i);
                productParam.setName(String.format("小米%d 全面屏游戏智能手机 6GB+64GB 黑色 全网通4G 双卡双待", i));
                productService.create(productParam);
            }
        }).run();


    }



//    @ApiOperation("获取前台商品详情")
//    @RequestMapping(value = "/detail/{id}", method = RequestMethod.GET)
//    @ResponseBody
//    public CommonResult<PmsPortalProductDetail> detail(@PathVariable Long id) {
//        PmsPortalProductDetail productDetail = portalProductService.detail(id);
//        return CommonResult.success(productDetail);
//    }
}
