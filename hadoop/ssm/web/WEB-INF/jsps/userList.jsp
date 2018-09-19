<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
  <head>
    <title>userList</title>
      <style type="text/css">
          table {
              border-collapse: collapse;
              margin: 0 auto;
              text-align: center;
              width: 50%;
          }

          table td, table th {
              border: 1px solid #cad9ea;
              color: #666;
              height: 30px;
          }

          table thead th {
              background-color: #CCE8EB;
              width: 100px;
          }

          table tr:nth-child(odd) {
              background: #fff;
          }

          table tr:nth-child(even) {
              background: #F5FAFA;
          }
      </style>
  </head>
  <body>

  <table >
      <tr style=" background: lightblue">
          <td>id</td>
          <td>name</td>
          <td>sex</td>
      </tr>

          <c:forEach items="${userList}" var="user">
              <tr>
                      <td><c:out value="${user.id}" ></c:out></td>
                      <td><c:out value="${user.name}" ></c:out></td>
                      <td><c:out value="${user.sex}" ></c:out></td>
              </tr>
          </c:forEach>
  </table>

  <%--<c:forEach items="${userList}" var="user">--%>
      <%--<c:out value="${user.id}" ></c:out>--%>
      <%--<c:out value="${user.name}" ></c:out>--%>
      <%--<c:out value="${user.sex}" ></c:out><br/>--%>
  <%--</c:forEach>--%>

  </body>
</html>
