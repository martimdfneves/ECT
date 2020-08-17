Imports System.Data.SqlClient

Public Class MainForm

    Dim CN As SqlConnection
    Dim CMD As SqlCommand

    Private Sub MainForm_Load(sender As Object, e As EventArgs) Handles MyBase.Load
        Me.CenterToScreen()
        'Local Server
        CN = New SqlConnection("Data Source = localhost;" &
                               "Initial Catalog = LojaDesporto; Integrated Security = true;")

        'IEETA Server
        'CN = New SqlConnection("Data Source = tcp:mednat.ieeta.pt\SQLSERVER,8101;" &
        '                       "Initial Catalog = p7g9; uid = p7g9; password = M88904_T88896")

        Try
            CN.Open()
            If CN.State = ConnectionState.Open Then
                MsgBox("Successful connection to database", MsgBoxStyle.OkOnly, "Connection Test")
            End If
        Catch ex As Exception
            MsgBox("FAILED TO OPEN CONNECTION TO DATABSE", MsgBoxStyle.Critical, "ERROR")
        End Try

        CMD = New SqlCommand
        CMD.Connection = CN
        CN.Close()

        Stores.loadStores()

        Panel1.Controls.Clear()
        Stores.TopLevel = False
        Panel1.Controls.Add(Stores)
        Stores.Show()
    End Sub

    'Cients Button
    Private Sub Button2_Click(sender As Object, e As EventArgs) Handles Button2.Click
        With Clients
            .loadClients()
            .TopLevel = False
            Panel1.Controls.Add(Clients)
            .BringToFront()
            .Show()
            clearClientsSection()
        End With
    End Sub

    'Stores Button
    Private Sub Button1_Click(sender As Object, e As EventArgs) Handles Button1.Click
        With Stores
            .TopLevel = False
            Panel1.Controls.Add(Stores)
            .BringToFront()
            .Show()
            .TextBoxSearch2.Text = ""
            clearStoresSection()
        End With
    End Sub

    'Workers Button
    Private Sub Button3_Click(sender As Object, e As EventArgs) Handles Button3.Click
        With Workers
            .loadStores()
            .TopLevel = False
            Panel1.Controls.Add(Workers)
            .BringToFront()
            .Show()
            clearWorkersSection()
        End With
    End Sub

    'Deliveries Button
    Private Sub Button4_Click(sender As Object, e As EventArgs) Handles Button4.Click
        With Deliveries
            .loadDeliveries()
            .TopLevel = False
            Panel1.Controls.Add(Deliveries)
            .BringToFront()
            .Show()
            clearDeliveriesSection()
        End With
    End Sub

    Private Sub clearStoresSection()
        With Stores
            .StoresDataGridView.ClearSelection()
            .ProductsDataGridView.DataSource = Nothing
            .WarehousesDataGridView.DataSource = Nothing
            .WarehousesProductsDataGridView.DataSource = Nothing
            .TextBoxName.Text = ""
            .TextBoxPrice.Text = ""
            .TextBoxUnits.Text = ""
            .TextBoxCode.Text = ""
            .TextBoxType.Text = ""
            .TextBoxName2.Text = ""
            .TextBoxPrice2.Text = ""
            .TextBoxUnits2.Text = ""
            .TextBoxCode2.Text = ""
            .TextBoxType2.Text = ""
            .TextBoxTotalStorage.Text = ""
            .TextBoxStorageOccupied.Text = ""
            .TextBoxSearch.Text = ""
            .Button11.Enabled = False
            .Button7.Enabled = False
            .Button6.Enabled = False
            .Button20.Enabled = False
            .Button22.Enabled = False
            .Button5.Enabled = False
            .Button8.Enabled = False
            .Button9.Enabled = False
            .Button13.Enabled = False
            .Button14.Enabled = False
            .TextBoxSearch.Enabled = False
            .TextBoxSearch2.Enabled = False
            .Label1.Visible = True
            .Label1.Enabled = False
            .ProductsDataGridView.DataSource = Nothing
            .WarehousesProductsDataGridView.DataSource = Nothing
        End With
    End Sub

    Private Sub clearClientsSection()
        With Clients
            .ClientsDataGridView.ClearSelection()
            .PurchasedProductsGridView.DataSource = Nothing
            .ReturnedProductsGridView.DataSource = Nothing
            .TextBoxSearch.Text = ""
            .Button5.Enabled = False
            .Button6.Enabled = False
            .Label2.Enabled = True
            .Label2.Visible = True
        End With
    End Sub

    Private Sub clearWorkersSection()
        With Workers
            .StoresDataGridView.ClearSelection()
            .WorkersDataGridView.DataSource = Nothing
            .SalesDataGridView.DataSource = Nothing
            .ReturnsDataGridView.DataSource = Nothing
            .Button3.Enabled = False
            .Button4.Enabled = False
            .TextBoxSearch.Enabled = False
            .TextBoxSearch.Text = ""
            .Label5.Visible = True
            .WorkersDataGridView.DataSource = Nothing
        End With
    End Sub

    Private Sub clearDeliveriesSection()
        With Deliveries
            .DeliveriesDataGridView.ClearSelection()
            .TextBoxID.Text = ""
            .TextBoxCode.Text = ""
            .TextBoxDate.Text = ""
            .TextBoxAmount.Text = ""
            .TextBoxDest.Text = ""
            .TextBoxStore.Text = ""
            .TextBoxID2.Text = ""
            .TextBoxCode2.Text = ""
            .TextBoxDate2.Text = ""
            .TextBoxAmount2.Text = ""
            .TextBoxDest2.Text = ""
            .TextBoxSearch.Text = ""
            .TextBoxStore2.Text = ""
            .Button1.Enabled = False
            .Button2.Enabled = False
            .Button3.Enabled = False
            .Button5.Enabled = False
            .Label3.Visible = True
            .Label3.Enabled = True
            .TextBoxDate.Enabled = False
            .TextBoxAmount.Enabled = False
            .TextBoxDest.Enabled = False
            .TextBoxStore.Enabled = False
        End With
    End Sub

End Class