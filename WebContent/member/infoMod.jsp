<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>

 
    <div class="container">
        <div class="box700" style="background-color:#ffffff; padding:20px; margin:20px auto">
            <h3>회원정보 수정</h3>
            <hr/>
            
            
            <div class="form-inline" style="margin-bottom:5px">
                <label style="width:100px">아이디</label>
                <input type="text" class="form-control" value="<%= request.getAttribute("a") %>"/>
            </div>
            
            <div class="form-inline" style="margin-bottom:5px">
                <label style="width:100px">이름</label>
                <input type="text" class="form-control" value="<%= request.getAttribute("b") %>"/>
            </div>    
            
            <div class="form-inline" style="margin-bottom:5px">
                <label style="width:100px">나이</label>
                <input type="text" class="form-control" value="<%= request.getAttribute("c") %>"/>
            </div>    
            
            <div class="form-inline" style="margin-bottom:5px">
                <label style="width:100px">전화번호</label>
                <select class="form-control">
                    <option>010</option>
                    <option>011</option>
                    <option>016</option>
                </select>
                <input style="width:60px" type="text" class="form-control"/>
                <input style="width:60px" type="text" class="form-control"/>
            </div>
            <hr/>
            
            <div align="center">
                <input type=submit class="btn btn-primary" value="수정완료"/>    
            </div>
        </div>
    
    </div>
 