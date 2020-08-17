Public Class EditClient
    Dim initialAddress As String
    Dim initialPhone As Integer

    'Form Loader
    Private Sub EditClient_Load(sender As Object, e As EventArgs) Handles MyBase.Load
        Dim index As Integer = Clients.ClientsDataGridView.CurrentRow.Index
        Dim selectedRow As DataGridViewRow = Clients.ClientsDataGridView.Rows(index)

        NIFTextBox.Text = selectedRow.Cells(0).Value.ToString()
        NameTextBox.Text = selectedRow.Cells(1).Value.ToString()
        AddressTextBox.Text = selectedRow.Cells(2).Value.ToString()
        PhoneTextBox.Text = selectedRow.Cells(3).Value.ToString()

        Me.initialAddress = AddressTextBox.Text
        Me.initialPhone = PhoneTextBox.Text
    End Sub

    'Cancel Button
    Private Sub Button7_Click(sender As Object, e As EventArgs) Handles CancelButton.Click
        AddressTextBox.Text = ""
        PhoneTextBox.Text = ""
    End Sub

    ' Phone Number TextBox
    Private Sub TextBox4_KeyPress(sender As Object, e As EventArgs) Handles PhoneTextBox.KeyPress
        NumberOnly(e)
    End Sub
    Private Sub NumberOnly(ByVal e As System.Windows.Forms.KeyPressEventArgs)
        If (Asc(e.KeyChar) >= 48 And Asc(e.KeyChar) <= 57) Or Asc(e.KeyChar) = 8 Then
        Else
            e.Handled = True
            MsgBox("Only Numeric Characteres are Allowed!", MsgBoxStyle.Information, "ERROR")
        End If
    End Sub

    ' Edit button
    Private Sub Button1_Click(sender As Object, e As EventArgs) Handles EditButton.Click
        If PhoneTextBox.Text.Length <> 9 Then
            MsgBox("Client's Phone Number Must Have 9 Numbers!", MsgBoxStyle.Information, "ERROR")
        Else
            Dim bolAddress As Boolean = addressChanged(AddressTextBox.Text.Trim)
            Dim bolPhone As Boolean = phoneChanged(PhoneTextBox.Text.Trim)
            If (bolAddress.Equals(True)) Then
                Clients.updateAddress(AddressTextBox.Text)
                Clients.Button5.Enabled = False
                Clients.Button6.Enabled = False
                Clients.PurchasedProductsGridView.DataSource = Nothing
                Clients.ReturnedProductsGridView.DataSource = Nothing
                Me.Close()
            End If
            If (bolPhone.Equals(True)) Then
                If (Clients.checkPhone(PhoneTextBox.Text) = False) Then
                    MsgBox("The Phone number inserted already exists!", MsgBoxStyle.Information, "ERROR")
                Else
                    Clients.updatePhone(PhoneTextBox.Text)
                    Clients.Button5.Enabled = False
                    Clients.Button6.Enabled = False
                    Clients.PurchasedProductsGridView.DataSource = Nothing
                    Clients.ReturnedProductsGridView.DataSource = Nothing
                    Me.Close()
                End If
            End If
        End If
    End Sub

    'To check if address was edited 
    Private Function addressChanged(ByVal recentAddress As String) As Boolean
        Dim tmp As Boolean = False
        If (Not (Me.initialAddress.Equals(recentAddress))) Then
            tmp = True
        End If
        Return tmp
    End Function

    'To check if phone number was edited
    Private Function phoneChanged(ByVal recentPhone As Integer) As Boolean
        Dim tmp As Boolean = False
        If (Not (Me.initialPhone.Equals(recentPhone))) Then
            tmp = True
        End If
        Return tmp
    End Function
End Class