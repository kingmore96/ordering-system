package com.example.demo.controller;

import com.example.demo.VO.CategoryVO;
import com.example.demo.VO.ProductInfoVO;
import com.example.demo.VO.ResultVO;
import com.example.demo.dataObjects.ProductCategory;
import com.example.demo.dataObjects.ProductInfo;
import com.example.demo.service.ProductCategoryService;
import com.example.demo.service.ProductInfoService;
import com.example.demo.utils.ResultVOUtil;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/buyer/product")
public class ProductInfoController {

    @Autowired
    private ProductInfoService productInfoService;

    @Autowired
    private ProductCategoryService productCategoryService;

    /**
     * 查询所有上架商品，类目带商品的展示
     * @return
     */
    @GetMapping("/list")
    public ResultVO list(){
        //查询上架商品
        List<ProductInfo> allUpProducts = productInfoService.findUpAll();

        //根据上架商品查出所有类目编号列表
        List<Integer> categoryTypeList = allUpProducts.stream().map(ProductInfo::getCategoryType).collect(Collectors.toList());

        //根据编号列表，查出所有商品类目
        List<ProductCategory> categoryList = productCategoryService.findByCategoryTypeIn(categoryTypeList);


        //构造拼装数据，循环类目编号列表，内循环上架商品
        List<CategoryVO> categoryVOList = getCategoryVOList(allUpProducts, categoryList);
        ResultVO success = ResultVOUtil.success(categoryVOList);
        return success;
    }

    /**
     * 获取ResultVO的Data
     * @param allUpProducts
     * @param categoryList
     * @return
     */
    private List<CategoryVO> getCategoryVOList(List<ProductInfo> allUpProducts, List<ProductCategory> categoryList) {
        List<CategoryVO> categoryVOList = new LinkedList<>();
        for (ProductCategory productCategory : categoryList) {
            CategoryVO categoryVO = new CategoryVO();
            categoryVO.setCategoryName(productCategory.getCategoryName());
            categoryVO.setCategoryType(productCategory.getCategoryType());

            List<ProductInfoVO> productInfoVOS = new LinkedList<>();
            for (ProductInfo productInfo : allUpProducts) {
                //如果属于这个类目，构造ProductInfoVo，并添加到List中
                if(productCategory.getCategoryType().equals(productInfo.getCategoryType())){
                    ProductInfoVO productInfoVO = new ProductInfoVO();
                    BeanUtils.copyProperties(productInfo,productInfoVO);
                    productInfoVOS.add(productInfoVO);
                }
            }
            categoryVO.setProductInfoVOList(productInfoVOS);
            categoryVOList.add(categoryVO);
        }
        return categoryVOList;
    }
}
