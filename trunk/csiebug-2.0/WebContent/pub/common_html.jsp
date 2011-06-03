<div id="alertDialog" title="var.title" style="position:relative;left:0;top:0; z-index:999;display:none;">
	<table align=center border=0 cellspacing=2 cellpadding=0>
		<tr>
			<td align="center" nowrap>var.message</td>
		</tr>
		<tr>
			<td align=center>
				<table>
					<tr>
						<td>
							<input type="button" id="alertDialogButton" name="alertDialogButton" onClick="closeAlertDialog();" value="var.button">
						</td>
					</tr>
				</table>
			</td>
		</tr>
	</table>
</div>

<div id="confirmDialog" title="var.title" style="position:relative;left:0;top:0; z-index:999;display:none;">
	<table align=center border=0 cellspacing=2 cellpadding=0>
		<tr>
			<td align="center" nowrap>var.message</td>
		</tr>
		<tr>
			<td align=center>
				<table>
					<tr>
						<td>
							<input type="button" id="confirmDialogOK" name="confirmDialogOK" onClick="closeConfirmDialog('var.objId', 'var.focusId', true, 'var.funcOK', 'var.funcCancel');" value="var.ok">
							<input type="button" id="confirmDialogCancel" name="confirmDialogCancel" onClick="closeConfirmDialog('var.objId', 'var.focusId', false, 'var.funcOK', 'var.funcCancel');" value="var.cancel">
						</td>
					</tr>
				</table>
			</td>
		</tr>
	</table>
</div>

<div id="waitingBlock" style="display:none;">
	<%=webutil.getMessage("common.PleaseWait") %>
</div>

<img id="waitButton" src="<%=webutil.getBasePathForHTML()%>images/gif_wait.gif" style="position:relative;left:0;top:0; z-index:5;display:none;">

<iframe id="downloadExcelIframe" name="downloadExcelIframe" width="0" height="0"></iframe>

<iframe id="uploadFileIframe" name="uploadFileIframe" width="0" height="0"></iframe>