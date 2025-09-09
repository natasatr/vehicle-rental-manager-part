<%@page import="java.time.LocalDate"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ page import="com.etfbl.ip.rentalcompany.beans.UserBean" %>
<%@ page import="com.etfbl.ip.rentalcompany.service.PromotionService" %>
<%@ page import="com.etfbl.ip.rentalcompany.service.PostService" %>
<%@ page import="com.etfbl.ip.rentalcompany.dto.PromotionDTO" %>
<%@ page import="com.etfbl.ip.rentalcompany.dto.PostDTO" %>
<%@ page import="com.etfbl.ip.rentalcompany.beans.PromotionBean" %>
<%@ page import="com.etfbl.ip.rentalcompany.beans.PostBean" %>
<%@ page import="java.util.List" %>
<%@ page import="java.time.format.DateTimeFormatter" %>

<%
    UserBean userBean = (UserBean)session.getAttribute("userBean");
    if (userBean == null || !userBean.isLoggedIn() || !"MANAGER".equals(userBean.getRole())) {
        response.sendRedirect(request.getContextPath() + "/pages/login.jsp");
        return;
    }

    PromotionService promotionService = new PromotionService();
    PostService postService = new PostService();

    String action = request.getParameter("action");
    if ("createPromotion".equals(action)) {
        String title = request.getParameter("title");
        String description = request.getParameter("description");
        int duration = Integer.parseInt(request.getParameter("duration"));
        LocalDate dur = LocalDate.now().plusDays(duration);

        PromotionBean promotionBean = new PromotionBean();
        promotionBean.setTitle(title);
        promotionBean.setDescription(description);
        promotionBean.setDuration(dur);

        promotionService.createPromotion(promotionBean);
    } else if ("createPost".equals(action)) {
        String title = request.getParameter("title");
        String content = request.getParameter("content");

        PostBean postBean = new PostBean();
        postBean.setTitle(title);
        postBean.setContent(content);

        postService.createPost(postBean);
    } else if ("logout".equals(action)) {
        session.invalidate();
        response.sendRedirect(request.getContextPath() + "/pages/login.jsp");
    }

    DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("dd.MM.yyyy. HH:mm");
%>
<!DOCTYPE html>
<html>
<head>
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Manager Dashboard</title>
    <link href="<%= request.getContextPath() %>/css/main.css" rel="stylesheet">
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
</head>
<body>
    <nav class="navbar navbar-expand-lg navbar-dark bg-dark mb-4">
        <div class="container">
            <a class="navbar-brand" href="#">Manager Dashboard</a>
            <div class="navbar-nav ms-auto">
                <span class="navbar-text welcome-text">Welcome, <%= userBean.getFirstName() %> <%= userBean.getLastName() %></span>
                <a class="btn btn-outline-light btn-sm" href="?action=logout">Logout</a>
            </div>
        </div>
    </nav>
    <div class="container">
        <div class="header text-center">
            <h1>Manager Dashboard</h1>
            <p class="lead">Manage promotions and posts</p>
        </div>

        <%
            int promPage = 1;
            String promPageParam = request.getParameter("promPage");
            if (promPageParam != null && !promPageParam.isEmpty()) {
                try { promPage = Integer.parseInt(promPageParam); } catch (NumberFormatException e) { promPage = 1; }
            }
            int promPerPage = 5;
            String searchTitle = request.getParameter("title");
            List<PromotionDTO> promotionResult;
            boolean isSearchProm = searchTitle != null && !searchTitle.isEmpty();
            int totalPromotions;
            int totalPromotionPages;

            if (isSearchProm) {
                promotionResult = promotionService.searchByTitle(searchTitle);
                totalPromotions = promotionResult.size();
                totalPromotionPages = 1;
                promPage = 1;
            } else {
                promotionResult = promotionService.getPaged(promPage, promPerPage);
                totalPromotions = promotionService.getTotalCount();
                totalPromotionPages = (int)Math.ceil((double)totalPromotions / promPerPage);
                if (totalPromotionPages == 0) totalPromotionPages = 1;
            }
        %>
        <div class="section-title">
            <h2>Promotions</h2>
            <div class="d-flex">
                <form method="get" action="main.jsp" class="d-flex me-2">
                    <input type="text" name="title" class="form-control" placeholder="Search by title" value="<%= (searchTitle != null) ? searchTitle : "" %>">
                    <button type="submit" class="btn btn-create ms-2">Search</button>
                </form>
                <button class="btn btn-create" data-bs-toggle="modal" data-bs-target="#createPromotionModal">Create Promotion</button>
            </div>
        </div>
        <div class="table-responsive">
            <table class="table table-hover custom-table custom-theme">
                <thead class="custom-thead">
                    <tr>
                        <th>ID</th>
                        <th>Title</th>
                        <th>Description</th>
                        <th>Duration</th>
                        <th>Created At</th>
                    </tr>
                </thead>
                <tbody class="custom-body">
                <% if (promotionResult.isEmpty()) { %>
                    <tr><td colspan="5" class="text-center">No promotions available.</td></tr>
                <% } else { int i=0; for (PromotionDTO promotion : promotionResult) { %>
                    <tr class="custom-row <%= (i % 2 == 0) ? "alt-row" : "" %>">
                        <td><%= promotion.getId() %></td>
                        <td><%= promotion.getTitle() %></td>
                        <td><%= promotion.getDescription() %></td>
                        <td><%= promotion.getDuration() %></td>
                        <td><%= promotion.getCreatedAt().format(dateTimeFormatter) %></td>
                    </tr>
                <% i++; } } %>
                </tbody>
            </table>
        </div>
        <% if (!isSearchProm) { %>
        <div class="pagination justify-content-center">
            <nav>
                <ul class="pagination">
                    <li class="page-item <%= promPage == 1 ? "disabled" : "" %>">
                        <a class="page-link" href="main.jsp?promPage=<%= promPage - 1 %>">Previous</a>
                    </li>
                    <% for (int i = 1; i <= totalPromotionPages; i++) { %>
                        <li class="page-item <%= i == promPage ? "active" : "" %>">
                            <a class="page-link" href="main.jsp?promPage=<%= i %>"><%= i %></a>
                        </li>
                    <% } %>
                    <li class="page-item <%= promPage >= totalPromotionPages ? "disabled" : "" %>">
                        <a class="page-link" href="main.jsp?promPage=<%= promPage + 1 %>">Next</a>
                    </li>
                </ul>
            </nav>
        </div>
        <% } %>

        <%
            int postPage = 1;
            String postPageParam = request.getParameter("postPage");
            if (postPageParam != null && !postPageParam.isEmpty()) {
                try { postPage = Integer.parseInt(postPageParam); } catch (NumberFormatException e) { postPage = 1; }
            }
            int postsPerPage = 5;
            String searchContent = request.getParameter("content");
            List<PostDTO> postResult;
            boolean isSearchPost = searchContent != null && !searchContent.isEmpty();
            int totalPosts;
            int totalPostPages;

            if (isSearchPost) {
                postResult = postService.searchByContent(searchContent);
                totalPosts = postResult.size();
                totalPostPages = 1;
                postPage = 1;
            } else {
                postResult = postService.getPaged(postPage, postsPerPage);
                totalPosts = postService.getTotalCount();
                totalPostPages = (int)Math.ceil((double)totalPosts / postsPerPage);
                if (totalPostPages == 0) totalPostPages = 1;
            }
        %>
        <div class="section-title">
            <h2>Posts</h2>
            <div class="d-flex">
                <form method="get" action="main.jsp" class="d-flex me-2">
                    <input type="text" name="content" class="form-control" placeholder="Search by content" value="<%= (searchContent != null) ? searchContent : "" %>">
                    <button type="submit" class="btn btn-create ms-2">Search</button>
                </form>
                <button class="btn btn-create" data-bs-toggle="modal" data-bs-target="#createPostModal">Create Post</button>
            </div>
        </div>
        <div class="table-responsive">
            <table class="table table-hover custom-table custom-theme">
                <thead class="custom-thead">
                    <tr>
                        <th>ID</th>
                        <th>Title</th>
                        <th>Content</th>
                        <th>Created At</th>
                    </tr>
                </thead>
                <tbody class="custom-body">
                <% if (postResult.isEmpty()) { %>
                    <tr><td colspan="4" class="text-center">No posts available.</td></tr>
                <% } else { int i=0; for (PostDTO post : postResult) { %>
                    <tr class="custom-row <%= (i % 2 == 0) ? "alt-row" : "" %>">
                        <td><%= post.getId() %></td>
                        <td><%= post.getTitle() %></td>
                        <td><%= post.getContent() %></td>
                        <td><%= post.getCreatedAt().format(dateTimeFormatter) %></td>
                    </tr>
                <% i++; } } %>
                </tbody>
            </table>
        </div>
        <% if (!isSearchPost) { %>
        <div class="pagination justify-content-center">
            <nav>
                <ul class="pagination">
                    <li class="page-item <%= postPage == 1 ? "disabled" : "" %>">
                        <a class="page-link" href="main.jsp?postPage=<%= postPage - 1 %>">Previous</a>
                    </li>
                    <% for (int i = 1; i <= totalPostPages; i++) { %>
                        <li class="page-item <%= i == postPage ? "active" : "" %>">
                            <a class="page-link" href="main.jsp?postPage=<%= i %>"><%= i %></a>
                        </li>
                    <% } %>
                    <li class="page-item <%= postPage >= totalPostPages ? "disabled" : "" %>">
                        <a class="page-link" href="main.jsp?postPage=<%= postPage + 1 %>">Next</a>
                    </li>
                </ul>
            </nav>
        </div>
        <% } %>
    </div>

    <div class="modal fade" id="createPromotionModal" tabindex="-1">
        <div class="modal-dialog">
            <div class="modal-content">
                <div class="modal-header"><h5 class="modal-title">Create Promotion</h5></div>
                <form method="post">
                    <input type="hidden" name="action" value="createPromotion">
                    <div class="modal-body">
                        <div class="mb-3">
                            <label for="title" class="form-label">Title</label>
                            <input type="text" class="form-control" id="title" name="title" required>
                        </div>
                        <div class="mb-3">
                            <label for="description" class="form-label">Description</label>
                            <textarea class="form-control" id="description" name="description" rows="3" required></textarea>
                        </div>
                        <div class="mb-3">
                            <label for="duration" class="form-label">Duration (days)</label>
                            <input type="number" class="form-control" id="duration" name="duration" min="1" required>
                        </div>
                    </div>
                    <div class="modal-footer">
                        <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Cancel</button>
                        <button type="submit" class="btn btn-primary">Create Promotion</button>
                    </div>
                </form>
            </div>
        </div>
    </div>

    <div class="modal fade" id="createPostModal" tabindex="-1">
        <div class="modal-dialog">
            <div class="modal-content">
                <div class="modal-header"><h5 class="modal-title">Create Post</h5></div>
                <form method="post">
                    <input type="hidden" name="action" value="createPost">
                    <div class="modal-body">
                        <div class="mb-3">
                            <label for="postTitle" class="form-label">Title</label>
                            <input type="text" class="form-control" id="postTitle" name="title" required>
                        </div>
                        <div class="mb-3">
                            <label for="content" class="form-label">Content</label>
                            <textarea class="form-control" id="content" name="content" rows="3" required></textarea>
                        </div>
                    </div>
                    <div class="modal-footer">
                        <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Cancel</button>
                        <button type="submit" class="btn btn-primary">Create Post</button>
                    </div>
                </form>
            </div>
        </div>
    </div>
</body>
</html>
