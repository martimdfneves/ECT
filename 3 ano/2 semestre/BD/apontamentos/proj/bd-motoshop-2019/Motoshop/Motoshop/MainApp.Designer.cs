namespace Motoshop
{
    partial class MainApp
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
            this.lbl_logged = new System.Windows.Forms.Label();
            this.client_tab_btn = new System.Windows.Forms.Button();
            this.staff_tab_btn = new System.Windows.Forms.Button();
            this.bike_tab_btn = new System.Windows.Forms.Button();
            this.store_tab_btn = new System.Windows.Forms.Button();
            this.content = new System.Windows.Forms.Panel();
            this.label1 = new System.Windows.Forms.Label();
            this.SuspendLayout();
            // 
            // lbl_logged
            // 
            this.lbl_logged.AutoSize = true;
            this.lbl_logged.Font = new System.Drawing.Font("Microsoft Sans Serif", 9.75F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((byte)(0)));
            this.lbl_logged.Location = new System.Drawing.Point(1019, 24);
            this.lbl_logged.Margin = new System.Windows.Forms.Padding(50, 15, 3, 0);
            this.lbl_logged.Name = "lbl_logged";
            this.lbl_logged.Size = new System.Drawing.Size(89, 16);
            this.lbl_logged.TabIndex = 22;
            this.lbl_logged.Text = "Logged in as:";
            // 
            // client_tab_btn
            // 
            this.client_tab_btn.BackColor = System.Drawing.SystemColors.ActiveCaption;
            this.client_tab_btn.Font = new System.Drawing.Font("Microsoft Sans Serif", 9.75F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((byte)(0)));
            this.client_tab_btn.Location = new System.Drawing.Point(488, 12);
            this.client_tab_btn.Name = "client_tab_btn";
            this.client_tab_btn.Size = new System.Drawing.Size(92, 41);
            this.client_tab_btn.TabIndex = 17;
            this.client_tab_btn.Text = "Clients";
            this.client_tab_btn.UseVisualStyleBackColor = true;
            this.client_tab_btn.Click += new System.EventHandler(this.client_tab_btn_Click);
            // 
            // staff_tab_btn
            // 
            this.staff_tab_btn.BackColor = System.Drawing.SystemColors.ActiveCaption;
            this.staff_tab_btn.Font = new System.Drawing.Font("Microsoft Sans Serif", 9.75F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((byte)(0)));
            this.staff_tab_btn.Location = new System.Drawing.Point(390, 12);
            this.staff_tab_btn.Name = "staff_tab_btn";
            this.staff_tab_btn.Size = new System.Drawing.Size(92, 41);
            this.staff_tab_btn.TabIndex = 19;
            this.staff_tab_btn.Text = "Staff";
            this.staff_tab_btn.UseVisualStyleBackColor = true;
            this.staff_tab_btn.Click += new System.EventHandler(this.staff_tab_btn_Click);
            // 
            // bike_tab_btn
            // 
            this.bike_tab_btn.BackColor = System.Drawing.SystemColors.ActiveCaption;
            this.bike_tab_btn.Font = new System.Drawing.Font("Microsoft Sans Serif", 9.75F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((byte)(0)));
            this.bike_tab_btn.Location = new System.Drawing.Point(292, 12);
            this.bike_tab_btn.Name = "bike_tab_btn";
            this.bike_tab_btn.Size = new System.Drawing.Size(92, 41);
            this.bike_tab_btn.TabIndex = 18;
            this.bike_tab_btn.Text = "Motorcycles";
            this.bike_tab_btn.UseVisualStyleBackColor = true;
            this.bike_tab_btn.Click += new System.EventHandler(this.bike_tab_btn_Click);
            // 
            // store_tab_btn
            // 
            this.store_tab_btn.BackColor = System.Drawing.SystemColors.ActiveCaption;
            this.store_tab_btn.Font = new System.Drawing.Font("Microsoft Sans Serif", 9.75F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((byte)(0)));
            this.store_tab_btn.Location = new System.Drawing.Point(194, 12);
            this.store_tab_btn.Name = "store_tab_btn";
            this.store_tab_btn.Size = new System.Drawing.Size(92, 41);
            this.store_tab_btn.TabIndex = 23;
            this.store_tab_btn.Text = "Store";
            this.store_tab_btn.UseVisualStyleBackColor = false;
            this.store_tab_btn.Click += new System.EventHandler(this.store_tab_btn_Click);
            // 
            // content
            // 
            this.content.Location = new System.Drawing.Point(0, 59);
            this.content.Name = "content";
            this.content.Size = new System.Drawing.Size(1264, 622);
            this.content.TabIndex = 24;
            // 
            // label1
            // 
            this.label1.AutoSize = true;
            this.label1.Font = new System.Drawing.Font("Microsoft Sans Serif", 26.25F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((byte)(0)));
            this.label1.Location = new System.Drawing.Point(12, 12);
            this.label1.Name = "label1";
            this.label1.Size = new System.Drawing.Size(174, 39);
            this.label1.TabIndex = 25;
            this.label1.Text = "MotoShop";
            // 
            // MainApp
            // 
            this.AutoScaleDimensions = new System.Drawing.SizeF(6F, 13F);
            this.AutoScaleMode = System.Windows.Forms.AutoScaleMode.Font;
            this.AutoSize = true;
            this.ClientSize = new System.Drawing.Size(1264, 681);
            this.Controls.Add(this.label1);
            this.Controls.Add(this.content);
            this.Controls.Add(this.store_tab_btn);
            this.Controls.Add(this.bike_tab_btn);
            this.Controls.Add(this.lbl_logged);
            this.Controls.Add(this.staff_tab_btn);
            this.Controls.Add(this.client_tab_btn);
            this.Name = "MainApp";
            this.Text = "MotoShop";
            this.Load += new System.EventHandler(this.MainApp_Load);
            this.ResumeLayout(false);
            this.PerformLayout();

        }

        #endregion

        private System.Windows.Forms.Label lbl_logged;
        private System.Windows.Forms.Button client_tab_btn;
        private System.Windows.Forms.Button staff_tab_btn;
        private System.Windows.Forms.Button bike_tab_btn;
        private System.Windows.Forms.Button store_tab_btn;
        private System.Windows.Forms.Panel content;
        private System.Windows.Forms.Label label1;
    }
}