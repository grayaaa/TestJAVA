package jfreechart;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartUtilities;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.labels.ItemLabelAnchor;
import org.jfree.chart.labels.ItemLabelPosition;
import org.jfree.chart.labels.StandardCategoryItemLabelGenerator;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.renderer.category.BarRenderer3D;
import org.jfree.chart.renderer.category.LineAndShapeRenderer;
import org.jfree.data.category.CategoryDataset;
import org.jfree.data.general.DatasetUtilities;
import org.jfree.ui.TextAnchor;

import java.awt.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 创建JFreeChart Line Chart（折线图）
 */
public class LineChartDemo {
    public static void main(String[] args) {
//        // 步骤1：创建CategoryDataset对象（准备数据）
//        CategoryDataset dataset = createDataset();
//        // 步骤2：根据Dataset 生成JFreeChart对象，以及做相应的设置
//        JFreeChart freeChart = createChart(dataset);
//        // 步骤3：将JFreeChart对象输出到文件，Servlet输出流等
//        saveAsFile(freeChart, "e://testline2.png", 600, 350);

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        try {
            Date date = sdf.parse("2014-07-16");
            SimpleDateFormat df = new SimpleDateFormat("MMdd");
            System.out.println(df.format(date));

        } catch (ParseException e) {
            e.printStackTrace();
        }


        

    }

    // 创建TimeSeriesCollection对象
    private static CategoryDataset createDataset() {

//        double[][] data = new double[][]{{1310.0, 500, 1110, 1000},
//                {720, 700, 680, 640},
//                {1130, 1020, 980, 800},
//                {440, 400, 360, 300},
//                {1440, 400, 1360, 300}};
//
//        String[] rowKeys = {"pig", "beef", "chicken", "fish", "dog"};
//        String[] columnKeys = {"Guangzhou", "Shenzhen", "Dongguan", "Foshan"};

        double[][] data = new double[][]{{23285, 0, 22831, 0, 0, 0, 0},
                {10698, 0, 12102, 0, 0, 0, 0}};

        String[] rowKeys = {"ipadpv", "ipaduv"};
        String[] columnKeys = {"2014-07-15", "2014-07-14", "2014-07-13", "2014-07-12", "2014-07-11", "2014-07-10", "2014-07-09"};
        CategoryDataset dataset2 = DatasetUtilities.createCategoryDataset(rowKeys, columnKeys, data);
        return dataset2;

    }

    private static void createChartPng(String[] rowKeys, String[] columnKeys,double[][] data) {

        CategoryDataset dataset = DatasetUtilities.createCategoryDataset(rowKeys, columnKeys, data);
        JFreeChart freeChart = createChart(dataset);
        saveAsFile(freeChart, "/tmp/testline2.png", 600, 350);

    }

    private static JFreeChart createBarChart(final CategoryDataset dataset) {
        JFreeChart chart = ChartFactory.createBarChart3D("肉类销量统计图",
                "type",
                "amount",
                dataset,
                PlotOrientation.VERTICAL,
                true,
                true,
                false);

        // 配置字体（解决中文乱码的通用方法）
        Font xfont = new Font("宋体", Font.PLAIN, 16); // X轴
        Font yfont = new Font("宋体", Font.PLAIN, 16); // Y轴
        Font kfont = new Font("宋体", Font.PLAIN, 14); // 底部
        Font titleFont = new Font("隶书", Font.BOLD, 20); // 图片标题

        chart.getLegend().setItemFont(kfont);
        chart.getTitle().setFont(titleFont);

        CategoryPlot plot = chart.getCategoryPlot();

        plot.getDomainAxis().setLabelFont(xfont);
        plot.getRangeAxis().setLabelFont(yfont);
//设置网格背景颜色
        plot.setBackgroundPaint(Color.white);
//设置网格竖线颜色
        plot.setDomainGridlinePaint(Color.pink);
//设置网格横线颜色
        plot.setRangeGridlinePaint(Color.pink);
//显示每个柱的数值，并修改该数值的字体属性
        BarRenderer3D renderer = new BarRenderer3D();
        renderer.setBaseItemLabelGenerator(new StandardCategoryItemLabelGenerator());
        renderer.setBaseItemLabelsVisible(true);
//默认的数字显示在柱子中，通过如下两句可调整数字的显示
//注意：此句很关键，若无此句，那数字的显示会被覆盖，给人数字没有显示出来的问题
        renderer.setBasePositiveItemLabelPosition(new ItemLabelPosition(ItemLabelAnchor.OUTSIDE12, TextAnchor.BASELINE_LEFT));
        renderer.setItemLabelAnchorOffset(10D);
//设置每个地区所包含的平行柱的之间距离
//renderer.setItemMargin(0.3);
        plot.setRenderer(renderer);
//设置地区、销量的显示位置
//将下方的“肉类”放到上方
        // plot.setDomainAxisLocation(AxisLocation.TOP_OR_RIGHT);
//将默认放在左边的“销量”放到右方
        //plot.setRangeAxisLocation(AxisLocation.BOTTOM_OR_RIGHT);


        return chart;
    }

    private static JFreeChart createChart(final CategoryDataset dataset) {

        // create the chart...
        final JFreeChart chart = ChartFactory.createLineChart(
                "统计折线图",       // chart title
                "统计类型",                    // domain axis label
                "值",                   // range axis label
                dataset,                   // data
                PlotOrientation.VERTICAL,  // orientation
                true,                      // include legend
                true,                      // tooltips
                false                      // urls
        );

        // 配置字体（解决中文乱码的通用方法）
        Font xfont = new Font("宋体", Font.PLAIN, 16); // X轴
        Font yfont = new Font("宋体", Font.PLAIN, 16); // Y轴
        Font kfont = new Font("宋体", Font.PLAIN, 14); // 底部
        Font titleFont = new Font("隶书", Font.BOLD, 20); // 图片标题

        chart.getLegend().setItemFont(kfont);
        chart.getTitle().setFont(titleFont);

        chart.setBackgroundPaint(Color.white);

        final CategoryPlot plot = (CategoryPlot) chart.getPlot();
        plot.getDomainAxis().setLabelFont(xfont);
        plot.getRangeAxis().setLabelFont(yfont);

        //设置网格背景颜色
        plot.setBackgroundPaint(Color.white);
//设置网格竖线颜色
        plot.setDomainGridlinePaint(Color.pink);
//设置网格横线颜色
        plot.setRangeGridlinePaint(Color.pink);

        // customise the range axis...
        final NumberAxis rangeAxis = (NumberAxis) plot.getRangeAxis();
        rangeAxis.setStandardTickUnits(NumberAxis.createIntegerTickUnits());
        rangeAxis.setAutoRangeIncludesZero(true);

        // customise the renderer...
        final LineAndShapeRenderer renderer = (LineAndShapeRenderer) plot.getRenderer();
//        renderer.setDrawShapes(true);

//        renderer.setSeriesStroke(
//                0, new BasicStroke(
//                        2.0f, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND,
//                        1.0f, new float[]{10.0f, 6.0f}, 0.0f
//                )
//        );
//        renderer.setSeriesStroke(
//                1, new BasicStroke(
//                        2.0f, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND,
//                        1.0f, new float[]{6.0f, 6.0f}, 0.0f
//                )
//        );
//        renderer.setSeriesStroke(
//                2, new BasicStroke(
//                        2.0f, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND,
//                        1.0f, new float[]{2.0f, 6.0f}, 0.0f
//                )
//        );

        return chart;
    }

    // 保存为文件
    public static void saveAsFile(JFreeChart chart, String outputPath,
                                  int weight, int height) {
        FileOutputStream out = null;
        try {
            File outFile = new File(outputPath);
            if (!outFile.getParentFile().exists()) {
                outFile.getParentFile().mkdirs();
            }
            out = new FileOutputStream(outputPath);
            // 保存为PNG文件
            ChartUtilities.writeChartAsPNG(out, chart, weight, height);
            // 保存为JPEG文件
            //ChartUtilities.writeChartAsJPEG(out, chart, 500, 400);
            out.flush();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (out != null) {
                try {
                    out.close();
                } catch (IOException e) {
                    // do nothing
                }
            }
        }
    }

}
