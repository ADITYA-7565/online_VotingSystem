<!DOCTYPE html>
<html>
<head>
    <title>Export Results</title>
    <link rel="stylesheet" href="css/style.css">
</head>
<body>

<jsp:include page="header.jsp" />

<div class="container center">
    <h1>Export Election Results</h1>

    <form action="export-results" method="post">
        <label>
            <input type="radio" name="format" value="csv" required>
            Export as CSV (Excel)
        </label>
        <br><br>
        <label>
            <input type="radio" name="format" value="pdf">
            Export as PDF
        </label>
        <br><br>

        <button type="submit">Download</button>
        <a href="admin" class="btn">Cancel</a>
    </form>
</div>

</body>
</html>
