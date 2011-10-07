
<%@ page import="edu.umn.ncs.ConsentAgreement" %>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
        <meta name="layout" content="main" />
        <g:set var="entityName" value="${message(code: 'consentAgreement.label', default: 'ConsentAgreement')}" />
        <title><g:message code="default.show.label" args="[entityName]" /></title>
    </head>
    <body>
        <div class="nav">
            <span class="menuButton"><a class="home" href="${createLink(uri: '/')}"><g:message code="default.home.label"/></a></span>
            <span class="menuButton"><g:link class="list" action="list"><g:message code="default.list.label" args="[entityName]" /></g:link></span>
            <span class="menuButton"><g:link class="create" action="create"><g:message code="default.new.label" args="[entityName]" /></g:link></span>
        </div>
        <div class="body">
            <h1><g:message code="default.show.label" args="[entityName]" /></h1>
            <g:if test="${flash.message}">
            <div class="message">${flash.message}</div>
            </g:if>
            <div class="dialog">
                <table>
                    <tbody>
                    
                        <tr class="prop">
                            <td valign="top" class="name"><g:message code="consentAgreement.id.label" default="Id" /></td>
                            
                            <td valign="top" class="value">${fieldValue(bean: consentAgreementInstance, field: "id")}</td>
                            
                        </tr>
                    
                        <tr class="prop">
                            <td valign="top" class="name"><g:message code="consentAgreement.agreementDate.label" default="Agreement Date" /></td>
                            
                            <td valign="top" class="value"><g:formatDate date="${consentAgreementInstance?.agreementDate}" /></td>
                            
                        </tr>
                    
                        <tr class="prop">
                            <td valign="top" class="name"><g:message code="consentAgreement.witnessName.label" default="Witness Name" /></td>
                            
                            <td valign="top" class="value">${fieldValue(bean: consentAgreementInstance, field: "witnessName")}</td>
                            
                        </tr>
                    
                        <tr class="prop">
                            <td valign="top" class="name"><g:message code="consentAgreement.witnessType.label" default="Witness Type" /></td>
                            
                            <td valign="top" class="value"><g:link controller="witnessType" action="show" id="${consentAgreementInstance?.witnessType?.id}">${consentAgreementInstance?.witnessType?.encodeAsHTML()}</g:link></td>
                            
                        </tr>
                    
                        <tr class="prop">
                            <td valign="top" class="name"><g:message code="consentAgreement.expirationDate.label" default="Expiration Date" /></td>
                            
                            <td valign="top" class="value"><g:formatDate date="${consentAgreementInstance?.expirationDate}" /></td>
                            
                        </tr>
                    
                        <tr class="prop">
                            <td valign="top" class="name"><g:message code="consentAgreement.sid.label" default="Sid" /></td>
                            
                            <td valign="top" class="value"><g:link controller="trackedItem" action="show" id="${consentAgreementInstance?.sid?.id}">${consentAgreementInstance?.sid?.encodeAsHTML()}</g:link></td>
                            
                        </tr>
                    
                        <tr class="prop">
                            <td valign="top" class="name"><g:message code="consentAgreement.alternatePersonId.label" default="Alternate Person Id" /></td>
                            
                            <td valign="top" class="value">${fieldValue(bean: consentAgreementInstance, field: "alternatePersonId")}</td>
                            
                        </tr>
                    
                        <tr class="prop">
                            <td valign="top" class="name"><g:message code="consentAgreement.createdByWhom.label" default="Created By Whom" /></td>
                            
                            <td valign="top" class="value">${fieldValue(bean: consentAgreementInstance, field: "createdByWhom")}</td>
                            
                        </tr>
                    
                        <tr class="prop">
                            <td valign="top" class="name"><g:message code="consentAgreement.createdWhen.label" default="Created When" /></td>
                            
                            <td valign="top" class="value"><g:formatDate date="${consentAgreementInstance?.createdWhen}" /></td>
                            
                        </tr>
                    
                        <tr class="prop">
                            <td valign="top" class="name"><g:message code="consentAgreement.lastUpdated.label" default="Last Updated" /></td>
                            
                            <td valign="top" class="value"><g:formatDate date="${consentAgreementInstance?.lastUpdated}" /></td>
                            
                        </tr>
                    
                        <tr class="prop">
                            <td valign="top" class="name"><g:message code="consentAgreement.consent.label" default="Consent" /></td>
                            
                            <td valign="top" class="value"><g:link controller="consentInstrument" action="show" id="${consentAgreementInstance?.consent?.id}">${consentAgreementInstance?.consent?.encodeAsHTML()}</g:link></td>
                            
                        </tr>
                    
                        <tr class="prop">
                            <td valign="top" class="name"><g:message code="consentAgreement.person.label" default="Person" /></td>
                            
                            <td valign="top" class="value"><g:link controller="person" action="show" id="${consentAgreementInstance?.person?.id}">${consentAgreementInstance?.person?.encodeAsHTML()}</g:link></td>
                            
                        </tr>
                    
                    </tbody>
                </table>
            </div>
            <div class="buttons">
                <g:form>
                    <g:hiddenField name="id" value="${consentAgreementInstance?.id}" />
                    <span class="button"><g:actionSubmit class="edit" action="edit" value="${message(code: 'default.button.edit.label', default: 'Edit')}" /></span>
                    <span class="button"><g:actionSubmit class="delete" action="delete" value="${message(code: 'default.button.delete.label', default: 'Delete')}" onclick="return confirm('${message(code: 'default.button.delete.confirm.message', default: 'Are you sure?')}');" /></span>
                </g:form>
            </div>
        </div>
    </body>
</html>
