

<%@ page import="edu.umn.ncs.ConsentAgreement" %>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
        <meta name="layout" content="main" />
        <g:set var="entityName" value="${message(code: 'consentAgreement.label', default: 'ConsentAgreement')}" />
        <title><g:message code="default.edit.label" args="[entityName]" /></title>
    </head>
    <body>
        <div class="nav">
            <span class="menuButton"><a class="home" href="${createLink(uri: '/')}"><g:message code="default.home.label"/></a></span>
            <span class="menuButton"><g:link class="list" action="list"><g:message code="default.list.label" args="[entityName]" /></g:link></span>
            <span class="menuButton"><g:link class="create" action="create"><g:message code="default.new.label" args="[entityName]" /></g:link></span>
        </div>
        <div class="body">
            <h1><g:message code="default.edit.label" args="[entityName]" /></h1>
            <g:if test="${flash.message}">
            <div class="message">${flash.message}</div>
            </g:if>
            <g:hasErrors bean="${consentAgreementInstance}">
            <div class="errors">
                <g:renderErrors bean="${consentAgreementInstance}" as="list" />
            </div>
            </g:hasErrors>
            <g:form method="post" >
                <g:hiddenField name="id" value="${consentAgreementInstance?.id}" />
                <g:hiddenField name="version" value="${consentAgreementInstance?.version}" />
                <div class="dialog">
                    <table>
                        <tbody>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                  <label for="agreementDate"><g:message code="consentAgreement.agreementDate.label" default="Agreement Date" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: consentAgreementInstance, field: 'agreementDate', 'errors')}">
                                    <g:datePicker name="agreementDate" precision="day" value="${consentAgreementInstance?.agreementDate}"  />
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                  <label for="witnessName"><g:message code="consentAgreement.witnessName.label" default="Witness Name" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: consentAgreementInstance, field: 'witnessName', 'errors')}">
                                    <g:textField name="witnessName" value="${consentAgreementInstance?.witnessName}" />
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                  <label for="witnessType"><g:message code="consentAgreement.witnessType.label" default="Witness Type" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: consentAgreementInstance, field: 'witnessType', 'errors')}">
                                    <g:select name="witnessType.id" from="${edu.umn.ncs.consent.WitnessType.list()}" optionKey="id" value="${consentAgreementInstance?.witnessType?.id}" noSelection="['null': '']" />
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                  <label for="expirationDate"><g:message code="consentAgreement.expirationDate.label" default="Expiration Date" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: consentAgreementInstance, field: 'expirationDate', 'errors')}">
                                    <g:datePicker name="expirationDate" precision="day" value="${consentAgreementInstance?.expirationDate}" default="none" noSelection="['': '']" />
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                  <label for="sid"><g:message code="consentAgreement.sid.label" default="Sid" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: consentAgreementInstance, field: 'sid', 'errors')}">
                                    <g:select name="sid.id" from="${edu.umn.ncs.TrackedItem.list()}" optionKey="id" value="${consentAgreementInstance?.sid?.id}" noSelection="['null': '']" />
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                  <label for="alternatePersonId"><g:message code="consentAgreement.alternatePersonId.label" default="Alternate Person Id" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: consentAgreementInstance, field: 'alternatePersonId', 'errors')}">
                                    <g:textField name="alternatePersonId" value="${fieldValue(bean: consentAgreementInstance, field: 'alternatePersonId')}" />
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                  <label for="createdByWhom"><g:message code="consentAgreement.createdByWhom.label" default="Created By Whom" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: consentAgreementInstance, field: 'createdByWhom', 'errors')}">
                                    <g:textField name="createdByWhom" value="${consentAgreementInstance?.createdByWhom}" />
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                  <label for="createdWhen"><g:message code="consentAgreement.createdWhen.label" default="Created When" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: consentAgreementInstance, field: 'createdWhen', 'errors')}">
                                    <g:datePicker name="createdWhen" precision="day" value="${consentAgreementInstance?.createdWhen}"  />
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                  <label for="consent"><g:message code="consentAgreement.consent.label" default="Consent" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: consentAgreementInstance, field: 'consent', 'errors')}">
                                    <g:select name="consent.id" from="${edu.umn.ncs.consent.ConsentInstrument.list()}" optionKey="id" value="${consentAgreementInstance?.consent?.id}"  />
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                  <label for="person"><g:message code="consentAgreement.person.label" default="Person" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: consentAgreementInstance, field: 'person', 'errors')}">
                                    <g:select name="person.id" from="${edu.umn.ncs.Person.list()}" optionKey="id" value="${consentAgreementInstance?.person?.id}"  />
                                </td>
                            </tr>
                        
                        </tbody>
                    </table>
                </div>
                <div class="buttons">
                    <span class="button"><g:actionSubmit class="save" action="update" value="${message(code: 'default.button.update.label', default: 'Update')}" /></span>
                    <span class="button"><g:actionSubmit class="delete" action="delete" value="${message(code: 'default.button.delete.label', default: 'Delete')}" onclick="return confirm('${message(code: 'default.button.delete.confirm.message', default: 'Are you sure?')}');" /></span>
                </div>
            </g:form>
        </div>
    </body>
</html>
