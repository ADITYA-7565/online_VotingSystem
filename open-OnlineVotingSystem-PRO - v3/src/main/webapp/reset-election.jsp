<!DOCTYPE html>
<html>
<head>
    <title>Confirm Election Reset</title>
    <link rel="stylesheet" href="css/style.css">
</head>
<body>

<div class="container center">
    <h1> Confirm Election Reset</h1>

    <p>
        This action will:
        <br> Delete all votes
        <br> Reset candidate vote counts
        <br> Start a new election
    </p>

    <form action="reset-election" method="post">
        <input type="text"
               name="electionName"
               placeholder="New Election Name"
               required>

        <br><br>

        <button style="background:#dc3545">
            Confirm Reset
        </button>
        <a href="admin" class="btn">Cancel</a>
    </form>
</div>

</body>
</html>
