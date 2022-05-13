<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <meta charset="UTF-8"/>
    <title>Notebook</title>
</head>
<body>
<nav><a href="${pageContext.request.contextPath}/jsp/add-user.jsp">Add user</a> | <a
        href="${pageContext.request.contextPath}/jsp/add-number.jsp">Add number</a></nav>
<ul>
    <c:forEach var="entry" items="${requestScope.notebook}">
        <li class="text">${entry.key}
            <c:if test="${entry.value.size() > 0}">
                <div class="content">
                    <ul>
                        <c:forEach var="number" items="${entry.value}">
                            <li class="text">${number}</li>
                        </c:forEach>
                    </ul>
                </div>
            </c:if>
        </li>
    </c:forEach>
</ul>
</body>
</html>