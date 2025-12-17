package com.springmvc.controller;

import com.springmvc.pojo.*;
import com.springmvc.service.*;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import java.util.*;

import java.util.HashSet;
import java.util.Set;

@Controller
@RequestMapping("/detail")
public class GoodsController {
    @Resource
    private WantedService wantedService;

    @Resource
    private GoodsService goodsService;

    @Resource
    private ImageService imageService;

    @Resource
    private CatelogService catelogService;

    @Resource
    private UserService userService;

    /**
     * 根据 闲置id查询该 闲置详细信息
     * @param id
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/detail/{id}")
    public ModelAndView getGoodsById(HttpSession session, @PathVariable("id") Integer id, @RequestParam(value = "str",required = false) String str) throws Exception {
        Random random = new Random();
        User user = (User)session.getAttribute("cur_user");
        Wanted wanted = null;
        if(user!=null){
            wanted = wantedService.selectWant(user.getId(),id);
        }


        //推荐闲置物品的集合
        List<Goods> commendGoods = new ArrayList<Goods>();
        //找出所有在售（status=1）的闲置商品
        List<Goods> list = goodsService.selectGoodsByStatusOrderByPolishTime((byte)1);

        // 推荐数量：取实际商品数和6的最小值
        int recommendCount = Math.min(list.size(), 6);
        
        // 使用Set避免推荐重复商品
        Set<Integer> selectedIndexes = new HashSet<>();
        while(commendGoods.size() < recommendCount && selectedIndexes.size() < list.size()){
            Integer num = random.nextInt(list.size());
            if(!selectedIndexes.contains(num)){
                selectedIndexes.add(num);
                Goods good = list.get(num);
                // 再次确认商品状态为在售且不是当前商品
                if(good.getStatus() == 1 && !good.getId().equals(id)){
                    commendGoods.add(good);
                }
            }
        }
        
        List<GoodsExtend> commendExtends = new ArrayList<GoodsExtend>();
        for(Goods good:commendGoods){
            GoodsExtend commendExtend = new GoodsExtend();
            List<Image> imageList = imageService.selectByGoodsPrimaryKey(good.getId());
            commendExtend.setGoods(good);
            commendExtend.setImages(imageList);
            commendExtends.add(commendExtend);
        }

        //找出当前闲置
        Goods goodsFind = goodsService.selectByPrimaryKey(id);
        goodsService.updateViewCountByPrimaryKey(id,goodsFind.getViewcount()+1);
        Goods goods = goodsService.selectByPrimaryKey(id);
        //找出当前 闲置的用户信息
        User seller = userService.selectByPrimaryKey(goods.getUserId());
        //找出分类信息
        Catelog catelog = catelogService.selectByPrimaryKey(goods.getCatelogId());
        //找到闲置对应的图片信息
        GoodsExtend goodsExtend = new GoodsExtend();
        List<Image> imageList = imageService.selectByGoodsPrimaryKey(id);
        goodsExtend.setGoods(goods);
        goodsExtend.setImages(imageList);
        //返回数据
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("goodsExtend", goodsExtend);
        modelAndView.addObject("want",wanted);
        modelAndView.addObject("seller", seller);
        modelAndView.addObject("search",str);
        modelAndView.addObject("commend", commendExtends);
        modelAndView.addObject("catelog", catelog);
        modelAndView.setViewName("/detail/detail");
        return modelAndView;
    }

}
