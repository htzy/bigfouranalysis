<%--
  Created by IntelliJ IDEA.
  User: root
  Date: 3/21/17
  Time: 7:09 AM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>${username}'s action analysis</title>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <meta http-equiv="x-ua-compatible" content="ie=edge">
    <link rel="icon" href="http://resources.huangshihe.com/bigfour/logo_white.jpg">
    <script src="https://img.hcharts.cn/jquery/jquery-1.8.3.min.js"></script>
    <script src="https://img.hcharts.cn/highcharts/highcharts.js"></script>
    <script src="https://img.hcharts.cn/highcharts/highcharts-more.js"></script>
    <script src="https://img.hcharts.cn/highcharts/modules/exporting.js"></script>
    <script src="https://img.hcharts.cn/highcharts-plugins/highcharts-zh_CN.js"></script>
    <script src="https://img.hcharts.cn/highcharts/themes/sand-signika.js"></script>
</head>
<body>

<div id="container" style="min-width: 400px; max-width: 600px; height: 400px; margin: 0 auto"></div>

</body>
<script type="text/javascript">
    $(function () {
        $('#container').highcharts({
            chart: {
                polar: true,
                type: 'line'
            },
            title: {
                text: '${username}数据分析',
                x: -80
            },
            pane: {
                size: '80%'
            },
            xAxis: {
                categories: ['操作数', '1', '2', '3','4', '5'],
                tickmarkPlacement: 'on',
                lineWidth: 0
            },
            yAxis: {
                gridLineInterpolation: 'polygon',
                lineWidth: 0,
                min: 0
            },
            tooltip: {
                shared: true,
                pointFormat: '<span style="color:{series.color}">{series.name}: <b>{point.y:,.0f}</b><br/>'
            },
            legend: {
                align: 'right',
                verticalAlign: 'top',
                y: 70,
                layout: 'vertical'
            },
            series: [{
                name: '平均比例',
                data: [${total}, 0.59, 900, 750, 970, 300],
                pointPlacement: 'on'
            }, {
                name: '${username}',
                data: [1615, 690, 980, 810, 560, 140],
                pointPlacement: 'on'
            }]
        });
    });

</script>
</html>


