Public Class RemoveWarehouseProduct
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
    Private Sub Button2_Click(sender As Object, e As EventArgs) Handles Button2.Click
        UnitsTextBox.Text = ""
    End Sub

    'Confirm Button
    Private Sub Button1_Click(sender As Object, e As EventArgs) Handles Button1.Click
        If UnitsTextBox.Text = "" Then
            MsgBox("Nº units is needed!", MsgBoxStyle.Information, "ERROR")
        Else
            Dim units As Integer = UnitsTextBox.Text
            Dim index As Integer = Stores.WarehousesProductsDataGridView.CurrentRow.Index
            Dim selectedRow As DataGridViewRow = Stores.WarehousesProductsDataGridView.Rows(index)
            Dim quant As Integer = selectedRow.Cells(2).Value
            If (units > quant) Then
                MsgBox("There's not " + units.ToString + " units of the selected product in the warehouse!",
                       MsgBoxStyle.Information, "ERROR")
            Else
                Stores.removeWarehouseProduct(units)
                Me.Close()
            End If
        End If
    End Sub
End Class