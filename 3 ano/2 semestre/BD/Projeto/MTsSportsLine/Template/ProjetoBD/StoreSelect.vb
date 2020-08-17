Imports System.Data.SqlClient

Public Class StoreSelect
    Dim CMD As SqlCommand
    Dim CN As SqlConnection = New SqlConnection("Data Source = localhost;" &
                                                "Initial Catalog = LojaDesporto; Integrated Security = true;")

    Private Sub FormLoad(sender As Object, e As EventArgs) Handles MyBase.Load
        Dim table As New DataTable()

        CMD = New SqlCommand()
        CMD.Connection = CN
        CMD.CommandText = "SELECT * FROM Projeto.Loja;"
        Dim adapter As New SqlDataAdapter(CMD)
        adapter.Fill(table)

        ComboBox1.DataSource = table
        ComboBox1.DisplayMember = "Nome"
        ComboBox1.ValueMember = "NumLoja"
    End Sub

    Public Function getStoreNum(name As String)
        Dim storenum As Integer

        CMD = New SqlCommand()
        CMD.Connection = CN
        CMD.CommandText = "SELECT Loja.NumLoja FROM Projeto.Loja WHERE Loja.Nome=@name;"
        CMD.Parameters.Add("@name", SqlDbType.VarChar, 30)
        CMD.Parameters("@name").Value = name
        CN.Open()
        storenum = CMD.ExecuteScalar()
        CN.Close()
        Return storenum
    End Function

    Private Sub Button1_Click(sender As Object, e As EventArgs) Handles Button1.Click
        Dim index As Integer = Deliveries.DeliveriesDataGridView.CurrentRow.Index
        Dim selectedRow As DataGridViewRow = Deliveries.DeliveriesDataGridView.Rows(index)
        Dim num As Integer = selectedRow.Cells(0).Value
        Dim store As Integer
        Dim stores As String
        stores = ComboBox1.Text
        store = getStoreNum(stores)

        CMD = New SqlCommand()
        CMD.Connection = CN
        CMD.CommandText = "EXEC Projeto.Remove_Delivery @num, @store"
        CMD.Parameters.Add("@num", SqlDbType.Int)
        CMD.Parameters.Add("@store", SqlDbType.Int)
        CMD.Parameters("@num").Value = num
        CMD.Parameters("@store").Value = store
        CN.Open()
        CMD.ExecuteScalar()
        Deliveries.loadDeliveries()
        CN.Close()
        Deliveries.TextBoxID.Text = ""
        Deliveries.TextBoxDate.Text = ""
        Deliveries.TextBoxAmount.Text = ""
        Deliveries.TextBoxDest.Text = ""
        Deliveries.TextBoxCode.Text = ""
        Deliveries.TextBoxStore.Text = ""
        Deliveries.DeliveriesDataGridView.ClearSelection()
        Me.Close()
        MessageBox.Show("Delivery canceled successfully")
    End Sub
End Class