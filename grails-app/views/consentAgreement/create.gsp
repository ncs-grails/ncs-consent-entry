

<%@ page import="edu.umn.ncs.ConsentAgreement" %>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
        <meta name="layout" content="ncs" />
        <g:set var="entityName" value="${message(code: 'consentAgreement.label', default: 'ConsentAgreement')}" />
        <title><g:message code="default.create.label" args="[entityName]" /></title>
    </head>
    <body>
        <div class="nav">
            <span class="menuButton"><a class="home" href="${createLink(uri: '/')}"><g:message code="default.home.label"/></a></span>
            <span class="menuButton"><g:link class="list" action="list"><g:message code="default.list.label" args="[entityName]" /></g:link></span>
        </div>
        <div class="body">
            <h1><g:message code="default.create.label" args="[entityName]" /></h1>
            <g:if test="${flash.message}">
            <div class="message">${flash.message}</div>
            </g:if>
            <g:hasErrors bean="${consentAgreementInstance}">
            <div class="errors">
                <g:renderErrors bean="${consentAgreementInstance}" as="list" />
            </div>
            </g:hasErrors>
            <<g:form action="save" method="post" >
				<g:hiddenField name="trackedItem.id" value="${consentAgreementInstance?.trackedItem?.id}" />
				<g:hiddenField name="person.id" value="${consentAgreementInstance?.person?.id}" />
				<g:hiddenField name="consent.id" value="${consentAgreementInstance?.consent?.id}" />
                <div class="dialog">
                    <table>
                        <tbody>
                        
                        	<tr class="prop">
                                <td valign="top" class="name">
                                    <label for="receiptDate"><g:message code="consentAgreement.receiptDate.label" default="Receipt Date" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: consentAgreementInstance, field: 'receiptDate', 'errors')}">
                                    <g:datePicker name="receiptDate" precision="day" />
                                </td>
                            </tr>
                            
                            <tr class="prop">
                                <td valign="top" class="name">
                                    <label for="agreementDate"><g:message code="consentAgreement.agreementDate.label" default="Agreement Date" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: consentAgreementInstance, field: 'agreementDate', 'errors')}">
                                    <g:datePicker name="agreementDate" precision="day" value="${consentAgreementInstance?.agreementDate}"  />
                                </td>
                            </tr>
                        
                        	<g:if test="${consentAgreementInstance?.consent?.enableWitness}">
					            <tr class="prop">
						            	<td valign="top" class="name">
						              		<label for="witnessName"><g:message code="consentAgreement.witnessName.label" default="Witness Name" /></label>
						            	</td>
						            	<td valign="top" class="value ${hasErrors(bean: consentAgreementInstance, field: 'witnessName', 'errors')}">
						            		<g:textField name="witnessName" value="${consentAgreementInstance?.witnessName}" />
										</td>
								</tr>
							 </g:if>
							  
                           <%--  <tr class="prop">
                                <td valign="top" class="name">
                                    <label for="trackedItem"><g:message code="consentAgreement.trackedItem.label" default="trackedItem" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: consentAgreementInstance, field: 'trackedItem', 'errors')}">
                                    <g:select name="trackedItem.id" from="${edu.umn.ncs.TrackedItem.list()}" optionKey="id" value="${consentAgreementInstance?.trackedItem?.id}" noSelection="['null': '']" />
                                </td>
                            </tr> --%>
                           
	                          <tr class="prop">
					           	<td valign="top" class="name">
					            	<label for="trackedItem"><g:message code="consentAgreement.trackedItem.label" default="trackedItem ID" /></label>
					            </td>
					            <td valign="top" class="value ${hasErrors(bean: consentAgreementInstance, field: 'trackedItem', 'errors')}">
								${consentAgreementInstance?.trackedItem?.id}
					          	</td>
					         </tr>
          
                            <%-- <tr class="prop">
                                <td valign="top" class="name">
                                    <label for="person"><g:message code="consentAgreement.person.label" default="Person" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: consentAgreementInstance, field: 'person', 'errors')}">
                                    <g:select name="person.id" from="${edu.umn.ncs.Person.list()}" optionKey="id" value="${consentAgreementInstance?.person?.id}"  />
                                </td>
                            </tr>--%> 
                        
                       	 <tr class="prop">
                       	 
			            	<td valign="top" class="name">
			              		<label for="person"><g:message code="consentAgreement.person.label" default="Person" /></label>
			            	</td>
			            	<td valign="top" class="value ${hasErrors(bean: consentAgreementInstance, field: 'person', 'errors')}">
								${consentAgreementInstance?.person?.fullName}
							</td>
			          	</tr>
          
				          <tr class="prop">
				          	<td valign="top" class="name">
				          		<label for="response"><g:message code="consentAgreement.response.label" default="Outcome response" /></label>
				          	</td>
				          <td valign="top" class="value ${hasErrors(bean: consentAgreementInstance, field: 'Consent', 'errors')}">
				          <g:each var="l" in="${consentResponseList}">
				            <span>
				              <g:radio name="responseCode.id" value="${l.id}" /> ${l.name}
				            </span>
				          </g:each>
          
          				</td>
          				</tr>
          				
                         <!-- replace with consentId -->
				         
						  <g:if test="${consentAgreementInstance?.consent?.hasChild}">
				          	<tr class="prop">
				          		<td valign="top" class="name">
				          			<label for="response"><g:message code="consentAgreement.response.label" default="Outcome response" /></label>
				          		</td>
				          	<td valign="top" class="value ${hasErrors(bean: consentAgreementInstance, field: 'Consent', 'errors')}">
				          	<g:each var="ls" in="${consentSecondaryResponseList}">
				            	<span>
				              	<g:radio name="secondaryResponseCode.id" value="${ls.id}" /> ${ls.name}
				            	</span>
				          	</g:each>
				          </g:if>
				          
                        </tbody>
                    </table>
                </div>
                <div class="buttons">
                    <span class="button"><g:submitButton name="create" class="save" value="${message(code: 'default.button.create.label', default: 'Create')}" /></span>
                </div>
            </g:form>
        </div>
    </body>
</html>
