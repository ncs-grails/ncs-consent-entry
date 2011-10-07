
<%@ page import="edu.umn.ncs.ConsentAgreement" %>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
        <meta name="layout" content="ncs" />
        <g:set var="entityName" value="${message(code: 'consentAgreement.label', default: 'Consent Agreement')}" />
        <title>Consent entry</title>
    </head>
    <body>
        <div class="nav">
            <span class="menuButton"><a class="home" href="${createLink(uri: '/')}"><g:message code="default.home.label"/></a></span>
            
        </div>
        <div class="body">
            <h1 style="border-width: 3px; border-style: double; text-align: center;">Consent</h1>
            <g:if test="${flash.message}">
            <div class="message">${flash.message}</div>
            </g:if>
            <g:if test="${errorMessage}">
            <div class="message">${errorMessage}</div>
            </g:if>
            <div class="dialog">
				<h2>Please enter TrackedItem ID:</h2>
                <g:form action="find">
					<g:textField name="id" value="${params.id}" />
                    <span class="button"><g:submitButton name="submit" value="${message(code: 'default.button.find.label', args='TeckedItem', default: 'Find')}" /></span>
                </g:form>
            </div>
            
            <g:if test="${trackedItemInstance}">
            	<ul>
            		<li>Person: ${trackedItemInstance?.person}</li>
            		<li>TrackedItem: ${trackedItemInstance?.id}</li>
                        <li>Item Type: ${trackedItemInstance?.batch?.primaryInstrument}</li>
            		<ul>
		        		<g:if test="${consentAgreementInstance}">
			        		<li>Item found. Already entered!</li>
			        		<!--<li><g:link action="edit" id="${consentAgreementInstance.id}">Click Here to Edit the consent</g:link></li>-->
		        		</g:if>
		        		<g:else>
			        		<li>No item entered yet.</li>
			        		<li><g:link action="create" id="${trackedItemInstance?.id}">Click Here to enter a new item</g:link></li>
						</g:else>
            		</ul>
            </g:if>
            
        </div>
    </body>
</html>

