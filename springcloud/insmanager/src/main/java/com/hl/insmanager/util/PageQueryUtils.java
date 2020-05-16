package com.hl.insmanager.util;
import com.github.pagehelper.ISelect;
import com.github.pagehelper.PageHelper;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;

public class PageQueryUtils {
    /**
     * 使用PageHelper进行分页查询，默认使用该方式
     *
     * @param pageNum  当前页码
     * @param pageSize 每页记录数
     * @param iSelect  数据查询
     * @param <R>      查询结果类型
     * @return 查询结果
     */
    public static <R> Page<R> queryList(int pageNum, int pageSize, ISelect iSelect) {
        com.github.pagehelper.Page<R> page = PageHelper.startPage(pageNum, pageSize).doSelectPage(iSelect);
        return new Page<>( page.getTotal(), page.getResult());
    }

    /**
     * 使用PageHelper进行分页查询，默认使用该方式
     *
     * @param pageNum  当前页码
     * @param pageSize 每页记录数
     * @param iSelect  数据查询
     * @param orderBy  排序
     * @param <R>      查询结果类型
     * @return 查询结果
     */
//    public static <R> Page<R> queryList(int pageNum, int pageSize, String orderBy,ISelect iSelect) {
//        com.github.pagehelper.Page<R> page = PageHelper.startPage(pageNum, pageSize,orderBy).doSelectPage(iSelect);
//        return new Page<>(page.getPageSize(), page.getPageNum(), page.getTotal(), page.getResult());
//    }

    /**
     * 手动分页查询，如果sql中使用了collection必须使用该方式
     *
     * @param pageNum     当前页码
     * @param pageSize    每页记录数
     * @param countQuery  求数据总数
     * @param selectQuery 数据查询
     * @param <R>         查询结果类型
     * @return 查询结果
     */
    public static <R> Page<R> queryList(int pageNum, int pageSize, Supplier<Long> countQuery, Function<Integer, List<R>> selectQuery) {
        final Long totalElement = countQuery.get();
        if (totalElement == 0) {
            return new Page<>(totalElement, new ArrayList<>());
        }

        int offset = (pageNum - 1) * pageSize;
        final List<R> result = selectQuery.apply(offset);
        return new Page<>( totalElement, result);
    }

    /**
     * 使用PageHelper进行分页查询，不指定页码，每次取指定数量的数据
     *
     * @param pageSize 每页记录数
     * @param iSelect  数据查询
     * @param <R>      查询结果类型
     * @return 查询结果
     */
    public static <R> List<R> queryList(int pageSize, ISelect iSelect) {
        final com.github.pagehelper.Page<R> page = PageHelper.offsetPage(0, pageSize, false).doSelectPage(iSelect);
        return page.getResult();
    }

    /**
     * 分页查找所有数据并处理
     *
     * @param pageSize      单次查询的记录条数,必须大于0
     * @param query         查询操作
     * @param recordHandler 对每条数据的处理操作
     * @param <R>           查询结果的实体
     */
    public static <R> void selectAll(int pageSize, Supplier<List<R>> query, Consumer<R> recordHandler) {
        int pageNum = 1;
        List<R> list;
        while (true) {
            //查找数据
            PageHelper.startPage(pageNum, pageSize);
            list = query.get();

            //数据处理
            list.forEach(recordHandler);

            //最后一页判断
            if (list.size() < pageSize) {
                break;
            }

            //下一次查询
            pageNum++;
        }
    }


    /**
     * 分页查找所有数据并处理
     *
     * @param query         查询操作
     * @param recordHandler 对每条数据的处理操作
     * @param <R>           查询结果的实体
     */
    public static <R> void selectAll(Supplier<List<R>> query, Consumer<R> recordHandler) {
        int pageSize = 200;
        selectAll(pageSize, query, recordHandler);
    }
}
