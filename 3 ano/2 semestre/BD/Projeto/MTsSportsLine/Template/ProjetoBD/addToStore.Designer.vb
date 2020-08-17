<Global.Microsoft.VisualBasic.CompilerServices.DesignerGenerated()> _
Partial Class addToStore
    Inherits System.Windows.Forms.Form

    'Form overrides dispose to clean up the component list.
    <System.Diagnostics.DebuggerNonUserCode()> _
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
    <System.Diagnostics.DebuggerStepThrough()> _
    Private Sub InitializeComponent()
        Me.UnitsTextBox = New System.Windows.Forms.TextBox()
        Me.Label9 = New System.Windows.Forms.Label()
        Me.Label2 = New System.Windows.Forms.Label()
        Me.CancelButton = New System.Windows.Forms.Button()
        Me.ConfirmButton = New System.Windows.Forms.Button()
        Me.SuspendLayout()
        '
        'UnitsTextBox
        '
        Me.UnitsTextBox.Location = New System.Drawing.Point(12, 63)
        Me.UnitsTextBox.Name = "UnitsTextBox"
        Me.UnitsTextBox.Size = New System.Drawing.Size(166, 20)
        Me.UnitsTextBox.TabIndex = 66
        '
        'Label9
        '
        Me.Label9.AutoSize = True
        Me.Label9.Location = New System.Drawing.Point(9, 47)
        Me.Label9.Name = "Label9"
        Me.Label9.Size = New System.Drawing.Size(46, 13)
        Me.Label9.TabIndex = 67
        Me.Label9.Text = "Nº Units"
        '
        'Label2
        '
        Me.Label2.AutoSize = True
        Me.Label2.Font = New System.Drawing.Font("Segoe UI", 15.75!, System.Drawing.FontStyle.Bold, System.Drawing.GraphicsUnit.Point, CType(0, Byte))
        Me.Label2.Location = New System.Drawing.Point(7, 5)
        Me.Label2.Name = "Label2"
        Me.Label2.Size = New System.Drawing.Size(260, 30)
        Me.Label2.TabIndex = 65
        Me.Label2.Text = "Add Product to the Store"
        '
        'CancelButton
        '
        Me.CancelButton.Location = New System.Drawing.Point(184, 77)
        Me.CancelButton.Name = "CancelButton"
        Me.CancelButton.Size = New System.Drawing.Size(75, 23)
        Me.CancelButton.TabIndex = 64
        Me.CancelButton.Text = "Cancel"
        Me.CancelButton.UseVisualStyleBackColor = True
        '
        'ConfirmButton
        '
        Me.ConfirmButton.Location = New System.Drawing.Point(184, 49)
        Me.ConfirmButton.Name = "ConfirmButton"
        Me.ConfirmButton.Size = New System.Drawing.Size(75, 23)
        Me.ConfirmButton.TabIndex = 63
        Me.ConfirmButton.Text = "Confirm"
        Me.ConfirmButton.UseVisualStyleBackColor = True
        '
        'addToStore
        '
        Me.AutoScaleDimensions = New System.Drawing.SizeF(6.0!, 13.0!)
        Me.AutoScaleMode = System.Windows.Forms.AutoScaleMode.Font
        Me.ClientSize = New System.Drawing.Size(271, 109)
        Me.Controls.Add(Me.UnitsTextBox)
        Me.Controls.Add(Me.Label9)
        Me.Controls.Add(Me.Label2)
        Me.Controls.Add(Me.CancelButton)
        Me.Controls.Add(Me.ConfirmButton)
        Me.Name = "addToStore"
        Me.Text = "addToStore"
        Me.ResumeLayout(False)
        Me.PerformLayout()

    End Sub

    Friend WithEvents UnitsTextBox As TextBox
    Friend WithEvents Label9 As Label
    Friend WithEvents Label2 As Label
    Friend WithEvents CancelButton As Button
    Friend WithEvents ConfirmButton As Button
End Class
