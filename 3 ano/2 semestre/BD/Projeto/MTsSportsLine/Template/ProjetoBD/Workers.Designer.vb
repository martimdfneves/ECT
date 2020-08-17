<Global.Microsoft.VisualBasic.CompilerServices.DesignerGenerated()>
Partial Class Workers
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
        Me.Label2 = New System.Windows.Forms.Label()
        Me.Label3 = New System.Windows.Forms.Label()
        Me.Label4 = New System.Windows.Forms.Label()
        Me.Button3 = New System.Windows.Forms.Button()
        Me.Button4 = New System.Windows.Forms.Button()
        Me.StoresDataGridView = New System.Windows.Forms.DataGridView()
        Me.WorkersDataGridView = New System.Windows.Forms.DataGridView()
        Me.SalesDataGridView = New System.Windows.Forms.DataGridView()
        Me.ReturnsDataGridView = New System.Windows.Forms.DataGridView()
        Me.TextBoxSearch = New System.Windows.Forms.TextBox()
        Me.Label5 = New System.Windows.Forms.Label()
        CType(Me.StoresDataGridView, System.ComponentModel.ISupportInitialize).BeginInit()
        CType(Me.WorkersDataGridView, System.ComponentModel.ISupportInitialize).BeginInit()
        CType(Me.SalesDataGridView, System.ComponentModel.ISupportInitialize).BeginInit()
        CType(Me.ReturnsDataGridView, System.ComponentModel.ISupportInitialize).BeginInit()
        Me.SuspendLayout()
        '
        'Label1
        '
        Me.Label1.AutoSize = True
        Me.Label1.Font = New System.Drawing.Font("Segoe UI", 20.0!, System.Drawing.FontStyle.Bold)
        Me.Label1.Location = New System.Drawing.Point(219, 21)
        Me.Label1.Name = "Label1"
        Me.Label1.Size = New System.Drawing.Size(119, 46)
        Me.Label1.TabIndex = 1
        Me.Label1.Text = "Stores"
        '
        'Label2
        '
        Me.Label2.AutoSize = True
        Me.Label2.Font = New System.Drawing.Font("Segoe UI", 20.0!, System.Drawing.FontStyle.Bold)
        Me.Label2.Location = New System.Drawing.Point(57, 314)
        Me.Label2.Name = "Label2"
        Me.Label2.Size = New System.Drawing.Size(154, 46)
        Me.Label2.TabIndex = 2
        Me.Label2.Text = "Workers"
        '
        'Label3
        '
        Me.Label3.AutoSize = True
        Me.Label3.Font = New System.Drawing.Font("Segoe UI", 20.0!, System.Drawing.FontStyle.Bold)
        Me.Label3.Location = New System.Drawing.Point(894, 21)
        Me.Label3.Name = "Label3"
        Me.Label3.Size = New System.Drawing.Size(100, 46)
        Me.Label3.TabIndex = 3
        Me.Label3.Text = "Sales"
        '
        'Label4
        '
        Me.Label4.AutoSize = True
        Me.Label4.Font = New System.Drawing.Font("Segoe UI", 20.0!, System.Drawing.FontStyle.Bold)
        Me.Label4.Location = New System.Drawing.Point(880, 314)
        Me.Label4.Name = "Label4"
        Me.Label4.Size = New System.Drawing.Size(143, 46)
        Me.Label4.TabIndex = 4
        Me.Label4.Text = "Returns"
        '
        'Button3
        '
        Me.Button3.Enabled = False
        Me.Button3.Location = New System.Drawing.Point(124, 558)
        Me.Button3.Margin = New System.Windows.Forms.Padding(3, 2, 3, 2)
        Me.Button3.Name = "Button3"
        Me.Button3.Size = New System.Drawing.Size(117, 33)
        Me.Button3.TabIndex = 10
        Me.Button3.Text = "Add"
        Me.Button3.UseVisualStyleBackColor = True
        '
        'Button4
        '
        Me.Button4.Enabled = False
        Me.Button4.Location = New System.Drawing.Point(337, 558)
        Me.Button4.Margin = New System.Windows.Forms.Padding(3, 2, 3, 2)
        Me.Button4.Name = "Button4"
        Me.Button4.Size = New System.Drawing.Size(117, 33)
        Me.Button4.TabIndex = 11
        Me.Button4.Text = "Remove"
        Me.Button4.UseVisualStyleBackColor = True
        '
        'StoresDataGridView
        '
        Me.StoresDataGridView.AllowUserToAddRows = False
        Me.StoresDataGridView.AllowUserToDeleteRows = False
        Me.StoresDataGridView.AllowUserToResizeColumns = False
        Me.StoresDataGridView.AllowUserToResizeRows = False
        Me.StoresDataGridView.ColumnHeadersHeightSizeMode = System.Windows.Forms.DataGridViewColumnHeadersHeightSizeMode.AutoSize
        Me.StoresDataGridView.Location = New System.Drawing.Point(41, 70)
        Me.StoresDataGridView.Margin = New System.Windows.Forms.Padding(4)
        Me.StoresDataGridView.MultiSelect = False
        Me.StoresDataGridView.Name = "StoresDataGridView"
        Me.StoresDataGridView.ReadOnly = True
        Me.StoresDataGridView.RowHeadersVisible = False
        Me.StoresDataGridView.RowHeadersWidth = 51
        Me.StoresDataGridView.SelectionMode = System.Windows.Forms.DataGridViewSelectionMode.FullRowSelect
        Me.StoresDataGridView.Size = New System.Drawing.Size(501, 174)
        Me.StoresDataGridView.TabIndex = 121
        '
        'WorkersDataGridView
        '
        Me.WorkersDataGridView.AllowUserToAddRows = False
        Me.WorkersDataGridView.AllowUserToDeleteRows = False
        Me.WorkersDataGridView.AllowUserToResizeColumns = False
        Me.WorkersDataGridView.AllowUserToResizeRows = False
        Me.WorkersDataGridView.ColumnHeadersHeightSizeMode = System.Windows.Forms.DataGridViewColumnHeadersHeightSizeMode.AutoSize
        Me.WorkersDataGridView.Location = New System.Drawing.Point(41, 363)
        Me.WorkersDataGridView.Margin = New System.Windows.Forms.Padding(4)
        Me.WorkersDataGridView.MultiSelect = False
        Me.WorkersDataGridView.Name = "WorkersDataGridView"
        Me.WorkersDataGridView.ReadOnly = True
        Me.WorkersDataGridView.RowHeadersVisible = False
        Me.WorkersDataGridView.RowHeadersWidth = 51
        Me.WorkersDataGridView.SelectionMode = System.Windows.Forms.DataGridViewSelectionMode.FullRowSelect
        Me.WorkersDataGridView.Size = New System.Drawing.Size(501, 174)
        Me.WorkersDataGridView.TabIndex = 122
        '
        'SalesDataGridView
        '
        Me.SalesDataGridView.AllowUserToAddRows = False
        Me.SalesDataGridView.AllowUserToDeleteRows = False
        Me.SalesDataGridView.AllowUserToResizeColumns = False
        Me.SalesDataGridView.AllowUserToResizeRows = False
        Me.SalesDataGridView.ColumnHeadersHeightSizeMode = System.Windows.Forms.DataGridViewColumnHeadersHeightSizeMode.AutoSize
        Me.SalesDataGridView.Location = New System.Drawing.Point(703, 70)
        Me.SalesDataGridView.Margin = New System.Windows.Forms.Padding(4)
        Me.SalesDataGridView.MultiSelect = False
        Me.SalesDataGridView.Name = "SalesDataGridView"
        Me.SalesDataGridView.ReadOnly = True
        Me.SalesDataGridView.RowHeadersVisible = False
        Me.SalesDataGridView.RowHeadersWidth = 51
        Me.SalesDataGridView.SelectionMode = System.Windows.Forms.DataGridViewSelectionMode.FullRowSelect
        Me.SalesDataGridView.Size = New System.Drawing.Size(501, 174)
        Me.SalesDataGridView.TabIndex = 123
        '
        'ReturnsDataGridView
        '
        Me.ReturnsDataGridView.AllowUserToAddRows = False
        Me.ReturnsDataGridView.AllowUserToDeleteRows = False
        Me.ReturnsDataGridView.AllowUserToResizeColumns = False
        Me.ReturnsDataGridView.AllowUserToResizeRows = False
        Me.ReturnsDataGridView.ColumnHeadersHeightSizeMode = System.Windows.Forms.DataGridViewColumnHeadersHeightSizeMode.AutoSize
        Me.ReturnsDataGridView.Location = New System.Drawing.Point(703, 363)
        Me.ReturnsDataGridView.Margin = New System.Windows.Forms.Padding(4)
        Me.ReturnsDataGridView.MultiSelect = False
        Me.ReturnsDataGridView.Name = "ReturnsDataGridView"
        Me.ReturnsDataGridView.ReadOnly = True
        Me.ReturnsDataGridView.RowHeadersVisible = False
        Me.ReturnsDataGridView.RowHeadersWidth = 51
        Me.ReturnsDataGridView.SelectionMode = System.Windows.Forms.DataGridViewSelectionMode.FullRowSelect
        Me.ReturnsDataGridView.Size = New System.Drawing.Size(501, 174)
        Me.ReturnsDataGridView.TabIndex = 124
        '
        'TextBoxSearch
        '
        Me.TextBoxSearch.Enabled = False
        Me.TextBoxSearch.Location = New System.Drawing.Point(227, 334)
        Me.TextBoxSearch.Margin = New System.Windows.Forms.Padding(4)
        Me.TextBoxSearch.Name = "TextBoxSearch"
        Me.TextBoxSearch.Size = New System.Drawing.Size(315, 22)
        Me.TextBoxSearch.TabIndex = 125
        '
        'Label5
        '
        Me.Label5.AutoSize = True
        Me.Label5.BackColor = System.Drawing.Color.White
        Me.Label5.Enabled = False
        Me.Label5.Location = New System.Drawing.Point(234, 337)
        Me.Label5.Name = "Label5"
        Me.Label5.Size = New System.Drawing.Size(298, 17)
        Me.Label5.TabIndex = 126
        Me.Label5.Text = "Search workers by name or employee number"
        '
        'Workers
        '
        Me.AutoScaleDimensions = New System.Drawing.SizeF(8.0!, 16.0!)
        Me.AutoScaleMode = System.Windows.Forms.AutoScaleMode.Font
        Me.BackgroundImageLayout = System.Windows.Forms.ImageLayout.None
        Me.ClientSize = New System.Drawing.Size(1267, 615)
        Me.Controls.Add(Me.Label5)
        Me.Controls.Add(Me.TextBoxSearch)
        Me.Controls.Add(Me.ReturnsDataGridView)
        Me.Controls.Add(Me.SalesDataGridView)
        Me.Controls.Add(Me.WorkersDataGridView)
        Me.Controls.Add(Me.StoresDataGridView)
        Me.Controls.Add(Me.Button4)
        Me.Controls.Add(Me.Button3)
        Me.Controls.Add(Me.Label4)
        Me.Controls.Add(Me.Label3)
        Me.Controls.Add(Me.Label2)
        Me.Controls.Add(Me.Label1)
        Me.FormBorderStyle = System.Windows.Forms.FormBorderStyle.None
        Me.Margin = New System.Windows.Forms.Padding(3, 2, 3, 2)
        Me.Name = "Workers"
        Me.RightToLeft = System.Windows.Forms.RightToLeft.No
        CType(Me.StoresDataGridView, System.ComponentModel.ISupportInitialize).EndInit()
        CType(Me.WorkersDataGridView, System.ComponentModel.ISupportInitialize).EndInit()
        CType(Me.SalesDataGridView, System.ComponentModel.ISupportInitialize).EndInit()
        CType(Me.ReturnsDataGridView, System.ComponentModel.ISupportInitialize).EndInit()
        Me.ResumeLayout(False)
        Me.PerformLayout()

    End Sub
    Friend WithEvents Label1 As Label
    Friend WithEvents Label2 As Label
    Friend WithEvents Label3 As Label
    Friend WithEvents Label4 As Label
    Friend WithEvents Button3 As Button
    Friend WithEvents Button4 As Button
    Friend WithEvents StoresDataGridView As DataGridView
    Friend WithEvents WorkersDataGridView As DataGridView
    Friend WithEvents SalesDataGridView As DataGridView
    Friend WithEvents ReturnsDataGridView As DataGridView
    Friend WithEvents TextBoxSearch As TextBox
    Friend WithEvents Label5 As Label
End Class
