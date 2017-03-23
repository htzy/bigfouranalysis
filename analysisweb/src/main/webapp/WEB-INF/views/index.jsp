<%--
  Created by IntelliJ IDEA.
  User: root
  Date: 3/21/17
  Time: 2:30 AM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <meta http-equiv="x-ua-compatible" content="ie=edge">
    <link rel="icon" href="http://resources.huangshihe.com/bigfour/logo_white.jpg">
    <script src="https://img.hcharts.cn/jquery/jquery-1.8.3.min.js"></script>
    <script src="https://img.hcharts.cn/highcharts/highcharts.js"></script>
    <script src="https://img.hcharts.cn/highcharts/highcharts-3d.js"></script>
    <script src="https://img.hcharts.cn/highcharts/modules/exporting.js"></script>
    <script src="https://img.hcharts.cn/highcharts-plugins/highcharts-zh_CN.js"></script>

</head>
<body>
<div id="container" style="min-width: 400px; max-width: 600px; height: 400px; margin: 0 auto"></div>
</body>
<script type="text/javascript">
    $(function () {
        $('#container').highcharts({
            chart: {
                type: 'pie',
                options3d: {
                    enabled: true,
                    alpha: 45
                }
            },
            title: {
                text: '活跃用户分布图'
            },
            subtitle: {
                text: '最多显示前十(按住shift点击查看详情)'
            },
            plotOptions: {
                pie: {
                    innerSize: 100,
                    depth: 45
                },
                series: {
                    cursor: 'pointer',
                    events: {
                        click: function (event) {
                            if (event.shiftKey) {
                                location.href = '${basePath}/analysisUserAction?username=' + event.point.name
                            }
                        }
                    }
                }
            },
            series: [{
                name: '操作记录',
                data: ${records}
            }]
        });
    });

</script>
</html>

