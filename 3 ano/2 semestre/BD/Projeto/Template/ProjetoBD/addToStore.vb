Public Class addToStore
    ' Nº Units TextBox
    Private Sub TextBox1_KeyPress(sender As Object, e As EventArgs) Handles UnitsTextBox.KeyPress
        NumberOnly(e)
    End Sub
    Private Sub NumberOnly(ByVal e As System.Windows.Forms.KeyPressEventArgs)
        If (Asc(e.KeyChar) >= 48 And Asc(e.KeyChar) <= 57) Or Asc(e.KeyChar) = 8 Then
        Else
            e.Handled = True
            MsgBox("Only numeric characteres are allowed!", MsgBoxStyle.Information, "ERROR")
        End If
    End Sub

    'Cancel Button
    Private Sub Button2_Click(sender As Object, e As EventArgs) Handles CancelButton.Click
        UnitsTextBox.Text = ""
    End Sub

    'Confirm Button
    Private Sub Button1_Click(sender As Object, e As EventArgs) Handles ConfirmButton.Click
        Dim index As Integer = Stores.WarehousesProductsDataGridView.CurrentRow.Index
        Dim selectedRow As DataGridViewRow = Stores.WarehousesProductsDataGridView.Rows(index)
        Dim Quant As Integer = selectedRow.Cells(2).Value

        If UnitsTextBox.Text = "" Then
            MsgBox("Nº units is needed!", MsgBoxStyle.Information, "ERROR")
        ElseIf (UnitsTextBox.Text > Quant) Then
            MsgBox("Number inserted is biger than the product's units available!", MsgBoxStyle.Information, "ERROR")
        Else
            Dim units As Integer = UnitsTextBox.Text
            Stores.warehouseToStore(units)
            Me.Close()
        End If
    End Sub
End Class