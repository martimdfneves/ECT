Imports System.Data.SqlClient

Public Class AddStore
    Dim CMD As SqlCommand
    Dim CN As SqlConnection = New SqlConnection("Data Source = localhost;" &
                                                "Initial Catalog = LojaDesporto; Integrated Security = true;")
    'Cancel Button
    Private Sub Button2_Click(sender As Object, e As EventArgs) Handles Button2.Click
        LocationText.Text = ""
        NameText.Text = ""
    End Sub

    'Location TextBox
    Private Sub TextBox1_KeyPress(sender As Object, e As EventArgs) Handles LocationText.KeyPress
        LettersOnly(e)
    End Sub

    Private Sub LettersOnly(ByVal e As System.Windows.Forms.KeyPressEventArgs)
        If (Asc(e.KeyChar) >= 48 And Asc(e.KeyChar) <= 57) Then
            e.Handled = True
            MsgBox("Only alphabetic characteres are allowed!", MsgBoxStyle.Information, "ERROR")
        End If
    End Sub

    'Confirm Button
    Private Sub Button1_Click(sender As Object, e As EventArgs) Handles Button1.Click
        If LocationText.Text = "" Or NameText.Text = "" Then
            MsgBox("Some textbox is empty!", MsgBoxStyle.Information, "ERROR")
        Else
            'To add in Stores DataGridView
            Dim storeName As String = NameText.Text
            Dim count As Integer = checkStore(storeName)
            If (count > 0) Then
                MsgBox("Store name already exists!", MsgBoxStyle.Information, "ERROR")
            Else
                Dim storeLocation As String = LocationText.Text
                Stores.addStore(storeName, storeLocation)
                Me.Close()
            End If
        End If
    End Sub

    Private Function checkStore(ByVal store As String) As Integer
        CMD = New SqlCommand()
        CMD.Connection = CN
        CMD.CommandText = "SELECT count(*) FROM Projeto.Loja WHERE Loja.Nome=@StoreName"
        CMD.Parameters.Add("@StoreName", SqlDbType.VarChar, 30)
        CMD.Parameters("@StoreName").Value = store
        CN.Open()
        Dim count As Integer = CMD.ExecuteScalar
        CN.Close()
        Return count
    End Function
End Class