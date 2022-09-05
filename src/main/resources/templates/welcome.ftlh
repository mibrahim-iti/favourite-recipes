<!DOCTYPE html>

<html>

<head>
    <meta charset="UTF-8">
    <title>Welcome</title>
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <link href="/css/main.css" rel="stylesheet">
    <link rel="stylesheet" href="https://www.w3schools.com/w3css/4/w3.css">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css">
</head>

<body>

<form onsubmit="return false;" action="" method="get" class="w3-container w3-card-4 w3-light-grey w3-text-blue"
      style="margin-top: 5%;margin-left: 5%;margin-right: 5%;">
    <br/>
    <h2 class="w3-center">Welcome to Favourite Recipes</h2>

    <br/>
    <br/>

    <p class="w3-center">
        <a href="${auth0AuthorizeUrl}" class="w3-button w3-section w3-blue w3-ripple">Login</a>
    </p>
    <br/>
</form>

<script src="/js/main.js"></script>

</body>

</html>
