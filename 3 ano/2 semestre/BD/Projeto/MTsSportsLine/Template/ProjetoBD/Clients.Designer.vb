<Global.Microsoft.VisualBasic.CompilerServices.DesignerGenerated()>
Partial Class Clients
    Inherits System.Windows.Forms.Form

    'Form overrides dispose to clean up the component list.
    <System.Diagnostics.DebuggerNonUserCode()>
    Protected Overrides Sub Dispose(ByVal disposing As Boolean)
        Try
            If disposing AndAlso components IsNot Nothing Then
                components.Dispose()
            End If
        Finally
            MyBase.Dispose(disposing)
        End Try
    End Sub

    'Required by the Windows Form Designer
    Private components As System.ComponentModel.IContainer

    'NOTE: The following procedure is required by the Windows Form Designer
    'It can be modified using the Windows Form Designer.  
    'Do not modify it using the code editor.
    <System.Diagnostics.DebuggerStepThrough()>
    Private Sub InitializeComponent()
        Me.Label1 = New System.Windows.Forms.Label()
        Me.Button5 = New System.Windows.Forms.Button()
        Me.Button6 = New System.Windows.Forms.Button()
        Me.GroupBox1 = New System.Windows.Forms.GroupBox()
        Me.Button7 = New System.Windows.Forms.Button()
        Me.PhoneTextBox = New System.Windows.Forms.TextBox()
        Me.Label6 = New System.Windows.Forms.Label()
        Me.AddressTextBox = New System.Windows.Forms.TextBox()
        Me.Label5 = New System.Windows.Forms.Label()
        Me.NameTextBox = New System.Windows.Forms.TextBox()
        Me.Label4 = New System.Windows.Forms.Label()
        Me.Label3 = New System.Windows.Forms.Label()
        Me.NIFTextBox = New System.Windows.Forms.TextBox()
        Me.Label7 = New System.Windows.Forms.Label()
        Me.Label8 = New System.Windows.Forms.Label()
        Me.ClientsDataGridView = New System.Windows.Forms.DataGridView()
        Me.PurchasedProductsGridView = New System.Windows.Forms.DataGridView()
        Me.ReturnedProductsGridView = New System.Windows.Forms.DataGridView()
        Me.TextBoxSearch = New System.Windows.Forms.TextBox()
        Me.Label2 = New System.Windows.Forms.Label()
        Me.GroupBox1.SuspendLayout()
        CType(Me.ClientsDataGridView, System.ComponentModel.ISupportInitialize).BeginInit()
        CType(Me.PurchasedProductsGridView, System.ComponentModel.ISupportInitialize).BeginInit()
        CType(Me.ReturnedProductsGridView, System.ComponentModel.ISupportInitialize).BeginInit()
        Me.SuspendLayout()
        '
        'Label1
        '
        Me.Label1.AutoSize = True
        Me.Label1.Font = New System.Drawing.Font("Segoe UI", 14.25!, System.Drawing.FontStyle.Bold, System.Drawing.GraphicsUnit.Point, CType(0, Byte))
        Me.Label1.Location = New System.Drawing.Point(14, 301)
        Me.Label1.Name = "Label1"
        Me.Label1.Size = New System.Drawing.Size(175, 25)
        Me.Label1.TabIndex = 77
        Me.Label1.Text = "Insert New Cliente"
        '
        'Button5
        '
        Me.Button5.Enabled = False
        Me.Button5.Location = New System.Drawing.Point(19, 269)
        Me.Button5.Name = "Button5"
        Me.Button5.Size = New System.Drawing.Size(162, 23)
        Me.Button5.TabIndex = 85
        Me.Button5.Text = "Remove"
        Me.Button5.UseVisualStyleBackColor = True
        '
        'Button6
        '
        Me.Button6.Enabled = False
        Me.Button6.Location = New System.Drawing.Point(226, 269)
        Me.Button6.Name = "Button6"
        Me.Button6.Size = New System.Drawing.Size(162, 23)
        Me.Button6.TabIndex = 86
        Me.Button6.Text = "Edit"
        Me.Button6.UseVisualStyleBackColor = True
        '
        'GroupBox1
        '
        Me.GroupBox1.Controls.Add(Me.Button7)
        Me.GroupBox1.Controls.Add(Me.PhoneTextBox)
        Me.GroupBox1.Controls.Add(Me.Label6)
        Me.GroupBox1.Controls.Add(Me.AddressTextBox)
        Me.GroupBox1.Controls.Add(Me.Label5)
        Me.GroupBox1.Controls.Add(Me.NameTextBox)
        Me.GroupBox1.Controls.Add(Me.Label4)
        Me.GroupBox1.Controls.Add(Me.Label3)
        Me.GroupBox1.Controls.Add(Me.NIFTextBox)
        Me.GroupBox1.Location = New System.Drawing.Point(19, 331)
        Me.GroupBox1.Name = "GroupBox1"
        Me.GroupBox1.Size = New System.Drawing.Size(369, 160)
        Me.GroupBox1.TabIndex = 87
        Me.GroupBox1.TabStop = False
        Me.GroupBox1.Text = "Client"
        '
        'Button7
        '
        Me.Button7.Location = New System.Drawing.Point(231, 121)
        Me.Button7.Name = "Button7"
        Me.Button7.Size = New System.Drawing.Size(126, 23)
        Me.Button7.TabIndex = 88
        Me.Button7.Text = "Insert"
        Me.Button7.UseVisualStyleBackColor = True
        '
        'PhoneTextBox
        '
        Me.PhoneTextBox.Location = New System.Drawing.Point(11, 123)
        Me.PhoneTextBox.Name = "PhoneTextBox"
        Me.PhoneTextBox.Size = New System.Drawing.Size(128, 20)
        Me.PhoneTextBox.TabIndex = 89
        '
        'Label6
        '
        Me.Label6.AutoSize = True
        Me.Label6.Location = New System.Drawing.Point(8, 107)
        Me.Label6.Name = "Label6"
        Me.Label6.Size = New System.Drawing.Size(78, 13)
        Me.Label6.TabIndex = 88
        Me.Label6.Text = "Phone Number"
        '
        'AddressTextBox
        '
        Me.AddressTextBox.Location = New System.Drawing.Point(11, 79)
        Me.AddressTextBox.Name = "AddressTextBox"
        Me.AddressTextBox.Size = New System.Drawing.Size(347, 20)
        Me.AddressTextBox.TabIndex = 89
        '
        'Label5
        '
        Me.Label5.AutoSize = True
        Me.Label5.Location = New System.Drawing.Point(8, 63)
        Me.Label5.Name = "Label5"
        Me.Label5.Size = New System.Drawing.Size(45, 13)
        Me.Label5.TabIndex = 88
        Me.Label5.Text = "Address"
        '
        'NameTextBox
        '
        Me.NameTextBox.Location = New System.Drawing.Point(107, 36)
        Me.NameTextBox.Name = "NameTextBox"
        Me.NameTextBox.Size = New System.Drawing.Size(250, 20)
        Me.NameTextBox.TabIndex = 91
        '
        'Label4
        '
        Me.Label4.AutoSize = True
        Me.Label4.Location = New System.Drawing.Point(104, 19)
        Me.Label4.Name = "Label4"
        Me.Label4.Size = New System.Drawing.Size(35, 13)
        Me.Label4.TabIndex = 90
        Me.Label4.Text = "Name"
        '
        'Label3
        '
        Me.Label3.AutoSize = True
        Me.Label3.Location = New System.Drawing.Point(8, 20)
        Me.Label3.Name = "Label3"
        Me.Label3.Size = New System.Drawing.Size(24, 13)
        Me.Label3.TabIndex = 88
        Me.Label3.Text = "NIF"
        '
        'NIFTextBox
        '
        Me.NIFTextBox.Location = New System.Drawing.Point(11, 36)
        Me.NIFTextBox.Name = "NIFTextBox"
        Me.NIFTextBox.Size = New System.Drawing.Size(86, 20)
        Me.NIFTextBox.TabIndex = 89
        '
        'Label7
        '
        Me.Label7.AutoSize = True
        Me.Label7.Font = New System.Drawing.Font("Segoe UI", 15.75!, System.Drawing.FontStyle.Bold, System.Drawing.GraphicsUnit.Point, CType(0, Byte))
        Me.Label7.Location = New System.Drawing.Point(427, 261)
        Me.Label7.Name = "Label7"
        Me.Label7.Size = New System.Drawing.Size(202, 30)
        Me.Label7.TabIndex = 88
        Me.Label7.Text = "Returned Products "
        '
        'Label8
        '
        Me.Label8.AutoSize = True
        Me.Label8.Font = New System.Drawing.Font("Segoe UI", 15.75!, System.Drawing.FontStyle.Bold, System.Drawing.GraphicsUnit.Point, CType(0, Byte))
        Me.Label8.Location = New System.Drawing.Point(427, 21)
        Me.Label8.Name = "Label8"
        Me.Label8.Size = New System.Drawing.Size(207, 30)
        Me.Label8.TabIndex = 89
        Me.Label8.Text = "Purchased Products"
        '
        'ClientsDataGridView
        '
        Me.ClientsDataGridView.AllowUserToAddRows = False
        Me.ClientsDataGridView.AllowUserToDeleteRows = False
        Me.ClientsDataGridView.AllowUserToResizeColumns = False
        Me.ClientsDataGridView.AllowUserToResizeRows = False
        Me.ClientsDataGridView.ColumnHeadersHeightSizeMode = System.Windows.Forms.DataGridViewColumnHeadersHeightSizeMode.AutoSize
        Me.ClientsDataGridView.Location = New System.Drawing.Point(19, 40)
        Me.ClientsDataGridView.MultiSelect = False
        Me.ClientsDataGridView.Name = "ClientsDataGridView"
        Me.ClientsDataGridView.ReadOnly = True
        Me.ClientsDataGridView.RowHeadersVisible = False
        Me.ClientsDataGridView.RowHeadersWidth = 51
        Me.ClientsDataGridView.SelectionMode = System.Windows.Forms.DataGridViewSelectionMode.FullRowSelect
        Me.ClientsDataGridView.Size = New System.Drawing.Size(369, 223)
        Me.ClientsDataGridView.TabIndex = 121
        '
        'PurchasedProductsGridView
        '
        Me.PurchasedProductsGridView.AllowUserToAddRows = False
        Me.PurchasedProductsGridView.AllowUserToDeleteRows = False
        Me.PurchasedProductsGridView.AllowUserToResizeColumns = False
        Me.PurchasedProductsGridView.AllowUserToResizeRows = False
        Me.PurchasedProductsGridView.ColumnHeadersHeightSizeMode = System.Windows.Forms.DataGridViewColumnHeadersHeightSizeMode.AutoSize
        Me.PurchasedProductsGridView.Location = New System.Drawing.Point(432, 51)
        Me.PurchasedProductsGridView.Name = "PurchasedProductsGridView"
        Me.PurchasedProductsGridView.ReadOnly = True
        Me.PurchasedProductsGridView.RowHeadersVisible = False
        Me.PurchasedProductsGridView.RowHeadersWidth = 51
        Me.PurchasedProductsGridView.SelectionMode = System.Windows.Forms.DataGridViewSelectionMode.FullRowSelect
        Me.PurchasedProductsGridView.Size = New System.Drawing.Size(505, 197)
        Me.PurchasedProductsGridView.TabIndex = 122
        '
        'ReturnedProductsGridView
        '
        Me.ReturnedProductsGridView.AllowUserToAddRows = False
        Me.ReturnedProductsGridView.AllowUserToDeleteRows = False
        Me.ReturnedProductsGridView.AllowUserToResizeColumns = False
        Me.ReturnedProductsGridView.AllowUserToResizeRows = False
        Me.ReturnedProductsGridView.ColumnHeadersHeightSizeMode = System.Windows.Forms.DataGridViewColumnHeadersHeightSizeMode.AutoSize
        Me.ReturnedProductsGridView.Location = New System.Drawing.Point(432, 294)
        Me.ReturnedProductsGridView.Name = "ReturnedProductsGridView"
        Me.ReturnedProductsGridView.ReadOnly = True
        Me.ReturnedProductsGridView.RowHeadersVisible = False
        Me.ReturnedProductsGridView.RowHeadersWidth = 51
        Me.ReturnedProductsGridView.SelectionMode = System.Windows.Forms.DataGridViewSelectionMode.FullRowSelect
        Me.ReturnedProductsGridView.Size = New System.Drawing.Size(505, 197)
        Me.ReturnedProductsGridView.TabIndex = 123
        '
        'TextBoxSearch
        '
        Me.TextBoxSearch.Location = New System.Drawing.Point(200, 15)
        Me.TextBoxSearch.Name = "TextBoxSearch"
        Me.TextBoxSearch.Size = New System.Drawing.Size(188, 20)
        Me.TextBoxSearch.TabIndex = 125
        '
        'Label2
        '
        Me.Label2.AutoSize = True
        Me.Label2.BackColor = System.Drawing.Color.White
        Me.Label2.Location = New System.Drawing.Point(207, 17)
        Me.Label2.Margin = New System.Windows.Forms.Padding(2, 0, 2, 0)
        Me.Label2.Name = "Label2"
        Me.Label2.Size = New System.Drawing.Size(149, 13)
        Me.Label2.TabIndex = 126
        Me.Label2.Text = "Search clients by name or NIF"
        '
        'Clients
        '
        Me.AutoScaleDimensions = New System.Drawing.SizeF(6.0!, 13.0!)
        Me.AutoScaleMode = System.Windows.Forms.AutoScaleMode.Font
        Me.ClientSize = New System.Drawing.Size(955, 508)
        Me.Controls.Add(Me.Label2)
        Me.Controls.Add(Me.TextBoxSearch)
        Me.Controls.Add(Me.ReturnedProductsGridView)
        Me.Controls.Add(Me.PurchasedProductsGridView)
        Me.Controls.Add(Me.ClientsDataGridView)
        Me.Controls.Add(Me.Label8)
        Me.Controls.Add(Me.Label7)
        Me.Controls.Add(Me.GroupBox1)
        Me.Controls.Add(Me.Button6)
        Me.Controls.Add(Me.Button5)
        Me.Controls.Add(Me.Label1)
        Me.FormBorderStyle = System.Windows.Forms.FormBorderStyle.None
        Me.Name = "Clients"
        Me.Text = "Clients"
        Me.GroupBox1.ResumeLayout(False)
        Me.GroupBox1.PerformLayout()
        CType(Me.ClientsDataGridView, System.ComponentModel.ISupportInitialize).EndInit()
        CType(Me.PurchasedProductsGridView, System.ComponentModel.ISupportInitialize).EndInit()
        CType(Me.ReturnedProductsGridView, System.ComponentModel.ISupportInitialize).EndInit()
        Me.ResumeLayout(False)
        Me.PerformLayout()

    End Sub
    Friend WithEvents Label1 As Label
    Friend WithEvents Button5 As Button
    Friend WithEvents Button6 As Button
    Friend WithEvents GroupBox1 As GroupBox
    Friend WithEvents Label3 As Label
    Friend WithEvents NIFTextBox As TextBox
    Friend WithEvents Label4 As Label
    Friend WithEvents NameTextBox As TextBox
    Friend WithEvents Label5 As Label
    Friend WithEvents AddressTextBox As TextBox
    Friend WithEvents Label6 As Label
    Friend WithEvents PhoneTextBox As TextBox
    Friend WithEvents Button7 As Button
    Friend WithEvents Label7 As Label
    Friend WithEvents Label8 As Label
    Friend WithEvents ClientsDataGridView As DataGridView
    Friend WithEvents PurchasedProductsGridView As DataGridView
    Friend WithEvents ReturnedProductsGridView As DataGridView
    Friend WithEvents TextBoxSearch As TextBox
    Friend WithEvents Label2 As Label
End Class
