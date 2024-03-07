<!-- <%@ page language="java" contentType="text/html; charset=ISO-8859-1"
pageEncoding="ISO-8859-1"%> -->
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link rel="stylesheet" href="assets/styles/dashboard.css">
    <title>CatBook</title>
    
</head>
<body>

    <header>
    
    <!-- <img src="assets/code.svg" alt="logo" class="logo"> -->

    <div class="rightItems">
      <h4 class="name">CatBook</h4>
    <div class="userName"><%= session.getAttribute("loggedInUser")%></div>
    </div>
    
      <nav class="nav-items">
        <ul>
          <li>
            <button class="button">
              Menu
            </button>
          </li>
          <li>
            <button class="button">
              Chats
            </button>
          </li>
          <li>
            <button class="button">
              Account
            </button>
          </li>
          <li>
            <button>Settings</button>
          </li>
        </ul>
      </nav>
      
    </header>
    
      <div class="container">
        <div class="breadcrumb">
          <a href="#">Dashboard</a>
            
          <a href="#">Users</a>

        </div>

        <div class="content">
            herer is container 2

            <table>
              <tr>
                <th>Login count </th>
                <th>Time</th>
                <th>Invalid Attempts</th>
              </tr>

              <tr>
                <td>1</td>
                <td>0.00</td>
                <td>0</td>
              </tr>
            </table>
        </div>
       </div>
       
    
    
    <script src="assets/scripts/dash.js"></script>
</body>
</html>