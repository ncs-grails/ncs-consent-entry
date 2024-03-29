
<%@ page import="edu.umn.ncs.ConsentAgreement" %>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
        <meta name="layout" content="ncs" />
        <g:set var="entityName" value="${message(code: 'consentAgreement.label', default: 'ConsentAgreement')}" />
        <title><g:message code="default.list.label" args="[entityName]" /></title>
    </head>
    <body>
        <div class="nav">
            <span class="menuButton"><a class="home" href="${createLink(uri: '/')}"><g:message code="default.home.label"/></a></span>
            <span class="menuButton"><g:link class="find" action="find"><g:message code="default.new.label" args="[entityName]" /></g:link></span>
        </div>
        <div class="body">
            <h1><g:message code="default.list.label" args="[entityName]" /></h1>
            <g:if test="${flash.message}">
            <div class="message">${flash.message}</div>
            </g:if>
            <div class="list">
                <table>
                    <thead>
                        <tr>
                        
                            <g:sortableColumn property="id" title="${message(code: 'consentAgreement.id.label', default: 'Id')}" />
                        
                            <g:sortableColumn property="agreementDate" title="${message(code: 'consentAgreement.agreementDate.label', default: 'Agreement Date')}" />
                        
                            <g:sortableColumn property="witnessName" title="${message(code: 'consentAgreement.witnessName.label', default: 'Witness Name')}" />
                        
                            <th><g:message code="consentAgreement.trackedItem.label" default="TrackedItem" /></th>
                        
                        </tr>
                    </thead>
                    <tbody>
                    <g:each in="${consentAgreementInstanceList}" status="i" var="consentAgreementInstance">
                        <tr class="${(i % 2) == 0 ? 'odd' : 'even'}">
                        
                            <td><g:link action="show" id="${consentAgreementInstance.id}">${fieldValue(bean: consentAgreementInstance, field: "id")}</g:link></td>
                        
                            <td><g:formatDate date="${consentAgreementInstance.agreementDate}" /></td>
                        
                            <td>${fieldValue(bean: consentAgreementInstance, field: "witnessName")}</td>
                        
                            <td>${fieldValue(bean: consentAgreementInstance, field: "trackedItem.id")}</td>
                        
                        </tr>
                    </g:each>
                    </tbody>
                </table>
            </div>
            <%-- <div class="paginateButtons">
                <g:paginate total="${consentAgreementInstanceTotal}" />
            </div> --%>
        </div>
    </body>
</html>
