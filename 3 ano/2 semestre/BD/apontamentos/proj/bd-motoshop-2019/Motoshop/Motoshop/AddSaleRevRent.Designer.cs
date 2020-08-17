using System;

namespace Motoshop
{
    partial class AddSaleRevRent
    {
        /// <summary>
        /// Required designer variable.
        /// </summary>
        private System.ComponentModel.IContainer components = null;

        /// <summary>
        /// Clean up any resources being used.
        /// </summary>
        /// <param name="disposing">true if managed resources should be disposed; otherwise, false.</param>
        protected override void Dispose(bool disposing)
        {
            if (disposing && (components != null))
            {
                components.Dispose();
            }
            base.Dispose(disposing);
        }

        #region Windows Form Designer generated code

        /// <summary>
        /// Required method for Designer support - do not modify
        /// the contents of this method with the code editor.
        /// </summary>
        private void InitializeComponent()
        {
            this.label17 = new System.Windows.Forms.Label();
            this.title_label = new System.Windows.Forms.Label();
            this.cancel_btn = new System.Windows.Forms.Button();
            this.add_btn = new System.Windows.Forms.Button();
            this.bike_cb = new System.Windows.Forms.ComboBox();
            this.client_cb = new System.Windows.Forms.ComboBox();
            this.client_label = new System.Windows.Forms.Label();
            this.staff_cb = new System.Windows.Forms.ComboBox();
            this.staff_label = new System.Windows.Forms.Label();
            this.SuspendLayout();
            // 
            // label17
            // 
            this.label17.AutoSize = true;
            this.label17.Location = new System.Drawing.Point(9, 38);
            this.label17.Name = "label17";
            this.label17.Size = new System.Drawing.Size(59, 13);
            this.label17.TabIndex = 30;
            this.label17.Text = "Motorcycle";
            // 
            // title_label
            // 
            this.title_label.Dock = System.Windows.Forms.DockStyle.Fill;
            this.title_label.Font = new System.Drawing.Font("Microsoft Sans Serif", 14.25F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((byte)(0)));
            this.title_label.Location = new System.Drawing.Point(0, 0);
            this.title_label.Name = "title_label";
            this.title_label.Padding = new System.Windows.Forms.Padding(0, 6, 0, 0);
            this.title_label.Size = new System.Drawing.Size(289, 211);
            this.title_label.TabIndex = 46;
            this.title_label.Text = "Register new Placeholder";
            this.title_label.TextAlign = System.Drawing.ContentAlignment.TopCenter;
            // 
            // cancel_btn
            // 
            this.cancel_btn.Location = new System.Drawing.Point(159, 170);
            this.cancel_btn.Name = "cancel_btn";
            this.cancel_btn.Size = new System.Drawing.Size(115, 32);
            this.cancel_btn.TabIndex = 48;
            this.cancel_btn.Text = "Cancel";
            this.cancel_btn.UseVisualStyleBackColor = true;
            this.cancel_btn.Click += new System.EventHandler(this.cancel_btn_Click);
            // 
            // add_btn
            // 
            this.add_btn.Location = new System.Drawing.Point(12, 170);
            this.add_btn.Name = "add_btn";
            this.add_btn.Size = new System.Drawing.Size(128, 32);
            this.add_btn.TabIndex = 47;
            this.add_btn.Text = "Add";
            this.add_btn.UseVisualStyleBackColor = true;
            this.add_btn.Click += new System.EventHandler(this.add_btn_Click);
            // 
            // bike_cb
            // 
            this.bike_cb.FormattingEnabled = true;
            this.bike_cb.Location = new System.Drawing.Point(12, 54);
            this.bike_cb.Name = "bike_cb";
            this.bike_cb.Size = new System.Drawing.Size(262, 21);
            this.bike_cb.TabIndex = 49;
            // 
            // client_cb
            // 
            this.client_cb.FormattingEnabled = true;
            this.client_cb.Location = new System.Drawing.Point(12, 96);
            this.client_cb.Name = "client_cb";
            this.client_cb.Size = new System.Drawing.Size(262, 21);
            this.client_cb.TabIndex = 51;
            // 
            // client_label
            // 
            this.client_label.AutoSize = true;
            this.client_label.Location = new System.Drawing.Point(9, 80);
            this.client_label.Name = "client_label";
            this.client_label.Size = new System.Drawing.Size(33, 13);
            this.client_label.TabIndex = 50;
            this.client_label.Text = "Client";
            // 
            // staff_cb
            // 
            this.staff_cb.FormattingEnabled = true;
            this.staff_cb.Location = new System.Drawing.Point(12, 138);
            this.staff_cb.Name = "staff_cb";
            this.staff_cb.Size = new System.Drawing.Size(262, 21);
            this.staff_cb.TabIndex = 53;
            // 
            // staff_label
            // 
            this.staff_label.AutoSize = true;
            this.staff_label.Location = new System.Drawing.Point(9, 122);
            this.staff_label.Name = "staff_label";
            this.staff_label.Size = new System.Drawing.Size(33, 13);
            this.staff_label.TabIndex = 52;
            this.staff_label.Text = "Seller";
            // 
            // AddSaleRevRent
            // 
            this.AutoScaleDimensions = new System.Drawing.SizeF(6F, 13F);
            this.AutoScaleMode = System.Windows.Forms.AutoScaleMode.Font;
            this.ClientSize = new System.Drawing.Size(289, 211);
            this.Controls.Add(this.staff_cb);
            this.Controls.Add(this.staff_label);
            this.Controls.Add(this.client_cb);
            this.Controls.Add(this.client_label);
            this.Controls.Add(this.bike_cb);
            this.Controls.Add(this.cancel_btn);
            this.Controls.Add(this.add_btn);
            this.Controls.Add(this.label17);
            this.Controls.Add(this.title_label);
            this.Name = "AddSaleRevRent";
            this.Text = "AddSaleRevRent";
            this.Load += new System.EventHandler(this.sale_rev_rent_load);
            this.ResumeLayout(false);
            this.PerformLayout();

        }

        #endregion
        private System.Windows.Forms.Label label17;
        private System.Windows.Forms.Label title_label;
        private System.Windows.Forms.Button cancel_btn;
        private System.Windows.Forms.Button add_btn;
        private System.Windows.Forms.ComboBox bike_cb;
        private System.Windows.Forms.ComboBox client_cb;
        private System.Windows.Forms.Label client_label;
        private System.Windows.Forms.ComboBox staff_cb;
        private System.Windows.Forms.Label staff_label;
    }
}