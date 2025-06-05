<!DOCTYPE html>
<html lang="en">
    <head>
        <meta charset="UTF-8" />
        <meta name="viewport" content="width=device-width, initial-scale=1.0" />
        <title>Error</title>
        <style>
            * {
                box-sizing: border-box;
            }
            body {
                background-color: red;
                margin: 0;
                height: 100vh;
                display: flex;
                flex-direction: column;
                justify-content: center;
                align-items: center;
                text-align: center;
                font-family: Arial, sans-serif;
            }
            h1 {
                font-size: 8vw;
                color: white;
                margin: 0 0 20px 0;
            }
            a {
                padding: 12px 24px;
                background-color: white;
                color: red;
                text-decoration: none;
                font-size: 1.2rem;
                font-weight: bold;
                border-radius: 8px;
                transition: background-color 0.3s ease, color 0.3s ease;
            }
            a:hover {
                background-color: #ffe5e5;
                color: darkred;
            }
        </style>
    </head>
    <body>
        <h1>Something went wrong</h1>
        <a href="login.jsp">Quay l?i</a>
    </body>
</html>
