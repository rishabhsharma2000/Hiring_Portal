<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>Forgot Password</title>
<style>
  body {
font-family: Arial, sans-serif;
background-color: #f0f0f0;
margin: 0;
display: flex;
justify-content: center;
align-items: center;
height: 100vh;
}
.forgot-container {
background-color: #ffffff;
padding: 20px;
border-radius: 5px;
box-shadow: 0px 2px 5px rgba(0, 0, 0, 0.1);
text-align: center;
}
.forgot-title {
font-size: 24px;
margin-bottom: 20px;
}
.forgot-form {
display: flex;
flex-direction: column;
}
.form-group {
margin-bottom: 15px;
}
.form-group label {
display: block;
font-weight: bold;
margin-bottom: 5px;
}
.form-group input {
width: 100%;
padding: 8px;
border: 1px solid #ccc;
border-radius: 3px;
font-size: 16px;
}
.submit-button {
background-color: #007bff;
color: #fff;
border: none;
border-radius: 3px;
padding: 10px 20px;
font-size: 16px;
cursor: pointer;
}
</style>
</head>
<body>
<div class="forgot-container">
    <h2 class="forgot-title">Forgot Password</h2>
    <form class="forgot-form">
      <div class="form-group">
        <label for="email">Email:</label>
        <input type="email" id="email" name="email" required>
      </div>
      <button class="submit-button" type="submit">Reset Password</button>
    </form>
  </div>
</body>
</html>



