<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>userList2</title>
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
<a href="/home/add3"><input type="button" value="添加用户"></a>
<table>
    <tr style=" background: lightblue">
        <td>id</td>
        <td>name</td>
        <td>sex</td>
        <td></td>
        <td></td>
    </tr>

    <c:forEach items="${userList}" var="user">
        <tr>
            <td><c:out value="${user.id}"></c:out></td>
            <td><c:out value="${user.name}"></c:out></td>
            <td><c:out value="${user.sex}"></c:out></td>

            <td>
                <a href='<c:url value="/home/update3?id=${user.id}"/>'>修改</a>
            </td>
            <td>
                <a href='<c:url value="/home/delete3?id=${user.id}"/>'>删除</a>
            </td>
        </tr>
    </c:forEach>

    <!--分页-->
    <tr>
        <td colspan="5">
            <c:forEach begin="1" end="${endPage}" var="pageNo">
                <a href='<c:url value="/home/selectAll3?pageNo=${pageNo}"/>'>第${pageNo}页</a>&nbsp;&nbsp;
                </a>
            </c:forEach>
        </td>
    </tr>
</table>

</body>
</html>
