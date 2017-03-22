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
    <title>${record.username}'s action analysis</title>
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
                text: '${record.username}数据统计',
                x: -80
            },
            pane: {
                size: '80%'
            },
            xAxis: {
                categories: ['ip', 'count', 'event', 'event_type','agent'],
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
                pointFormat: '<span style="color:{series.color}">{series.name}: <b>{point.y:,.3f}</b><br/>'
            },
            legend: {
                align: 'right',
                verticalAlign: 'top',
                y: 70,
                layout: 'vertical'
            },
            series: [{
                name: '平均占比',
                data: [${total.ip}, ${total.count}, ${total.event}, ${total.event_type}, ${total.agent}],
                pointPlacement: 'on'
            }, {
                name: '${record.username}占比',
                data: [${record.ip}, ${record.count}, ${record.event}, ${record.event_type}, ${record.agent}],
                pointPlacement: 'on'
            }]
        });
    });

</script>
</html>


