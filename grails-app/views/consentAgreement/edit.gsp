

<%@ page import="edu.umn.ncs.ConsentAgreement" %>

<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
        <meta name="layout" content="ncs" />
        <g:set var="entityName" value="${message(code: 'consentAgreement.label', default: 'ConsentAgreement')}" />
        <title><g:message code="default.edit.label" args="[entityName]" /></title>
    </head>
    <body>
        <div class="nav">
            <span class="menuButton"><a class="home" href="${createLink(uri: '/')}"><g:message code="default.home.label"/></a></span>
            <span class="menuButton"><g:link class="list" action="list"><g:message code="default.list.label" args="[entityName]" /></g:link></span>
            <span class="menuButton"><g:link class="find" action="find"><g:message code="default.new.label" args="[entityName]" /></g:link></span>
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
								<tr class="prop">
										<td valign="top" class="name">
						              		<label for="witnessType"><g:message code="consentAgreement.witnessType.label" default="Witness Type" /></label>
						            	</td>
						            	
						            	<td valign="top" class="value ${hasErrors(bean: consentAgreementInstance, field: 'withnessType', 'errors')}">
                                    		<g:select name="witnessType.id" from="${edu.umn.ncs.consent.WitnessType.list()}" optionKey="id" value="${consentAgreementInstance?.witnessType?.id}"  />
                                		</td>
                                </tr>
							 </g:if>
							
							 <tr class="prop">
					           	<td valign="top" class="name">
					            	<label for="trackedItem"><g:message code="consentAgreement.trackedItem.label" default="trackedItem ID" /></label>
					            </td>
					            <td valign="top" class="value ${hasErrors(bean: consentAgreementInstance, field: 'trackedItem', 'errors')}">
								${consentAgreementInstance?.trackedItem?.id}
					          	</td>
					         </tr>
                            
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
				              <g:if test="${outcomeResponse.id == l.outcomeResponseCode.id }">  <g:radio name="responseCode.id" value="${l.id}" checked="checked"/> </g:if>
				              <g:else>  <g:radio name="responseCode.id" value="${l.id}"/> </g:else>
				             ${l.outcomeResponseCode} 
				            </span>
				          </g:each>
          
          				</td>
          				</tr>
          				 
						  <g:if test="${consentAgreementInstance?.consent?.hasChild}">
						  
						  <g:if test="${consentAgreementInstance?.consent?.hasOtherAgreementDate}">
	          				   <tr class="prop">
	                                <td valign="top" class="name">
	                                    <label for="agreementDate"><g:message code="consentAgreement.agreementDate.label" default="Agreement Date" /></label>
	                                </td>
	                                <td valign="top" class="value ${hasErrors(bean: consentAgreementInstance, field: 'agreementDate', 'errors')}">
	                                    <g:datePicker name="secondaryAgreementDate" precision="day" value="${consentAgreementInstance?.agreementDate}"  />
	                                </td>
	                            </tr>
	                        </g:if>
	                        
				          	<tr class="prop">
				          		<td valign="top" class="name">
				          			<label for="response"><g:message code="consentAgreement.response.label" default="Outcome response" /></label>
				          		</td>
				          	<td valign="top" class="value ${hasErrors(bean: consentAgreementInstance, field: 'Consent', 'errors')}">
				          	<g:each var="ls" in="${consentSecondaryResponseList}">
				            	<span>
				            		<g:if test="${secondaryOutcomeResponse.id == ls.outcomeResponseCode.id }">  <g:radio name="secondaryResponseCode.id" value="${ls.id}" checked="checked"/> </g:if>
				              		<g:else>  <g:radio name="secondaryResponseCode.id" value="${ls.id}"/> </g:else>
				              		${ls.outcomeResponseCode}
				              
				              	<%-- <g:radio name="secondaryResponseCode.id" value="${ls.id}" /> ${ls} --%>
				            	</span>
				          	</g:each>
				          </g:if>
                        
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

