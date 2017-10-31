<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>服务异常</title>
</head>
<body class="body-500">
<div class="container">
    <section class="error-wrapper">
        <i class="icon-500"></i>
        <h1>无法满足你的访问了！</h1>
        <#if error??>
            <div>你访问的路径：${(error.path)!'/#'}${(error.url)!''}</div>
        </#if>
        <p class="page-500"><a href="/">返回首页</a></p>
    </section>
</div>
</body>
</html>