package top.imyzt.ms.core.page;

import cn.hutool.core.util.StrUtil;
import cn.hutool.extra.servlet.ServletUtil;
import com.baomidou.mybatisplus.plugins.Page;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import top.imyzt.ms.core.exception.MsException;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 *
 * <p>
 *     分页参数.可在此创建多个分页方式.接收不同前台发送的分页参数
 * </p>
 *
 * @author: imyzt
 * @email imyzt01@gmail.com
 * @date: 2018/4/26 16:27
 */
public class PageFactory<T> {

    public Page<T> defaultPage(){

        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        Map<String, String> params = ServletUtil.getParamMap(request);

        if (!params.containsKey("current") || !params.containsKey("size")){
            throw new MsException("缺少分页参数");
        }

        //第几页
        Integer current = Integer.valueOf(params.get("current"));
        //每页大小
        Integer size = Integer.valueOf(params.get("size"));
        //排序字段
        String sort = params.get("sort");
        //asc或desc,升序或降序
        String order = params.get("order");

        if (StrUtil.isBlank(sort)){
            Page<T> page = new Page<>(current,size);
            page.setOpenSort(false);
            return page;
        }else {
            Page<T> page = new Page<>(current,size,sort);
            boolean asc = Order.ASC.getDes().equals(order);
            page.setAsc(asc);
            return page;
        }

    }
}
