using System;
using System.Windows.Forms;

namespace Motoshop
{
    partial class Motorcycles
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
            this.stock_grid_view = new System.Windows.Forms.DataGridView();
            this.rent_grid_view = new System.Windows.Forms.DataGridView();
            this.btn_stock = new System.Windows.Forms.Button();
            this.btn_rent = new System.Windows.Forms.Button();
            this.label4 = new System.Windows.Forms.Label();
            this.label2 = new System.Windows.Forms.Label();
            this.label1 = new System.Windows.Forms.Label();
            this.txt_year = new System.Windows.Forms.TextBox();
            this.txt_frame = new System.Windows.Forms.TextBox();
            this.label3 = new System.Windows.Forms.Label();
            this.label5 = new System.Windows.Forms.Label();
            this.txt_model = new System.Windows.Forms.TextBox();
            this.txt_brand = new System.Windows.Forms.TextBox();
            this.label6 = new System.Windows.Forms.Label();
            this.label7 = new System.Windows.Forms.Label();
            this.txt_hp = new System.Windows.Forms.TextBox();
            this.txt_cc = new System.Windows.Forms.TextBox();
            this.groupBox1 = new System.Windows.Forms.GroupBox();
            this.gb_stock = new System.Windows.Forms.GroupBox();
            this.txt_price = new System.Windows.Forms.TextBox();
            this.label9 = new System.Windows.Forms.Label();
            this.lb_stand = new System.Windows.Forms.ListBox();
            this.label8 = new System.Windows.Forms.Label();
            this.gb_rent = new System.Windows.Forms.GroupBox();
            this.txt_pricekm = new System.Windows.Forms.TextBox();
            this.label12 = new System.Windows.Forms.Label();
            this.txt_totalkm = new System.Windows.Forms.TextBox();
            this.label10 = new System.Windows.Forms.Label();
            this.lb_motostation = new System.Windows.Forms.ListBox();
            this.label11 = new System.Windows.Forms.Label();
            this.btn_insert = new System.Windows.Forms.Button();
            this.btn_remove = new System.Windows.Forms.Button();
            this.label13 = new System.Windows.Forms.Label();
            this.txt_brand_edit = new System.Windows.Forms.TextBox();
            this.txt_model_edit = new System.Windows.Forms.TextBox();
            this.txt_frame_edit = new System.Windows.Forms.TextBox();
            this.txt_year_edit = new System.Windows.Forms.TextBox();
            this.txt_cc_edit = new System.Windows.Forms.TextBox();
            this.txt_hp_edit = new System.Windows.Forms.TextBox();
            this.button2 = new System.Windows.Forms.Button();
            this.label14 = new System.Windows.Forms.Label();
            this.label15 = new System.Windows.Forms.Label();
            this.label16 = new System.Windows.Forms.Label();
            this.label17 = new System.Windows.Forms.Label();
            this.label18 = new System.Windows.Forms.Label();
            this.label19 = new System.Windows.Forms.Label();
            this.txt_price_edit = new System.Windows.Forms.TextBox();
            this.lbl_price = new System.Windows.Forms.Label();
            this.txt_pricekm_edit = new System.Windows.Forms.TextBox();
            this.lbl_pricekm = new System.Windows.Forms.Label();
            this.lbl_totalkm = new System.Windows.Forms.Label();
            this.txt_totalkm_edit = new System.Windows.Forms.TextBox();
            this.txt_local_edit = new System.Windows.Forms.TextBox();
            this.label20 = new System.Windows.Forms.Label();
            this.btn_update = new System.Windows.Forms.Button();
            this.label21 = new System.Windows.Forms.Label();
            ((System.ComponentModel.ISupportInitialize)(this.stock_grid_view)).BeginInit();
            ((System.ComponentModel.ISupportInitialize)(this.rent_grid_view)).BeginInit();
            this.groupBox1.SuspendLayout();
            this.gb_stock.SuspendLayout();
            this.gb_rent.SuspendLayout();
            this.SuspendLayout();
            // 
            // stock_grid_view
            // 
            this.stock_grid_view.AllowUserToAddRows = false;
            this.stock_grid_view.AllowUserToDeleteRows = false;
            this.stock_grid_view.AllowUserToResizeRows = false;
            this.stock_grid_view.AutoSizeColumnsMode = System.Windows.Forms.DataGridViewAutoSizeColumnsMode.Fill;
            this.stock_grid_view.ColumnHeadersHeightSizeMode = System.Windows.Forms.DataGridViewColumnHeadersHeightSizeMode.AutoSize;
            this.stock_grid_view.Location = new System.Drawing.Point(12, 51);
            this.stock_grid_view.Name = "stock_grid_view";
            this.stock_grid_view.ReadOnly = true;
            this.stock_grid_view.RowHeadersVisible = false;
            this.stock_grid_view.Size = new System.Drawing.Size(340, 332);
            this.stock_grid_view.TabIndex = 21;
            this.stock_grid_view.CellClick += new System.Windows.Forms.DataGridViewCellEventHandler(this.stock_grid_view_CellClick);
            // 
            // rent_grid_view
            // 
            this.rent_grid_view.AllowUserToAddRows = false;
            this.rent_grid_view.AllowUserToDeleteRows = false;
            this.rent_grid_view.AllowUserToResizeRows = false;
            this.rent_grid_view.AutoSizeColumnsMode = System.Windows.Forms.DataGridViewAutoSizeColumnsMode.Fill;
            this.rent_grid_view.ColumnHeadersHeightSizeMode = System.Windows.Forms.DataGridViewColumnHeadersHeightSizeMode.AutoSize;
            this.rent_grid_view.Location = new System.Drawing.Point(12, 51);
            this.rent_grid_view.Name = "rent_grid_view";
            this.rent_grid_view.ReadOnly = true;
            this.rent_grid_view.RowHeadersVisible = false;
            this.rent_grid_view.Size = new System.Drawing.Size(340, 332);
            this.rent_grid_view.TabIndex = 22;
            this.rent_grid_view.Visible = false;
            this.rent_grid_view.CellClick += new System.Windows.Forms.DataGridViewCellEventHandler(this.rent_grid_view_CellClick);
            // 
            // btn_stock
            // 
            this.btn_stock.BackColor = System.Drawing.Color.Silver;
            this.btn_stock.Location = new System.Drawing.Point(12, 432);
            this.btn_stock.Name = "btn_stock";
            this.btn_stock.Size = new System.Drawing.Size(167, 37);
            this.btn_stock.TabIndex = 23;
            this.btn_stock.Text = "Stock";
            this.btn_stock.UseVisualStyleBackColor = false;
            this.btn_stock.Click += new System.EventHandler(this.btn_stock_Click);
            // 
            // btn_rent
            // 
            this.btn_rent.BackColor = System.Drawing.Color.Transparent;
            this.btn_rent.Location = new System.Drawing.Point(185, 432);
            this.btn_rent.Name = "btn_rent";
            this.btn_rent.Size = new System.Drawing.Size(167, 37);
            this.btn_rent.TabIndex = 24;
            this.btn_rent.Text = "Rent";
            this.btn_rent.UseVisualStyleBackColor = false;
            this.btn_rent.Click += new System.EventHandler(this.btn_rent_Click);
            // 
            // label4
            // 
            this.label4.AutoSize = true;
            this.label4.Font = new System.Drawing.Font("Microsoft Sans Serif", 15.75F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((byte)(0)));
            this.label4.Location = new System.Drawing.Point(850, 12);
            this.label4.Name = "label4";
            this.label4.Size = new System.Drawing.Size(224, 25);
            this.label4.TabIndex = 25;
            this.label4.Text = "Insert New Motorcycle";
            // 
            // label2
            // 
            this.label2.AutoSize = true;
            this.label2.Location = new System.Drawing.Point(105, 77);
            this.label2.Name = "label2";
            this.label2.Size = new System.Drawing.Size(29, 13);
            this.label2.TabIndex = 29;
            this.label2.Text = "Year";
            // 
            // label1
            // 
            this.label1.AutoSize = true;
            this.label1.Location = new System.Drawing.Point(26, 77);
            this.label1.Name = "label1";
            this.label1.Size = new System.Drawing.Size(53, 13);
            this.label1.TabIndex = 28;
            this.label1.Text = "Frame No";
            // 
            // txt_year
            // 
            this.txt_year.Location = new System.Drawing.Point(108, 93);
            this.txt_year.Name = "txt_year";
            this.txt_year.Size = new System.Drawing.Size(72, 20);
            this.txt_year.TabIndex = 33;
            // 
            // txt_frame
            // 
            this.txt_frame.Location = new System.Drawing.Point(29, 93);
            this.txt_frame.Name = "txt_frame";
            this.txt_frame.Size = new System.Drawing.Size(73, 20);
            this.txt_frame.TabIndex = 32;
            // 
            // label3
            // 
            this.label3.AutoSize = true;
            this.label3.Location = new System.Drawing.Point(194, 32);
            this.label3.Name = "label3";
            this.label3.Size = new System.Drawing.Size(36, 13);
            this.label3.TabIndex = 33;
            this.label3.Text = "Model";
            // 
            // label5
            // 
            this.label5.AutoSize = true;
            this.label5.Location = new System.Drawing.Point(26, 32);
            this.label5.Name = "label5";
            this.label5.Size = new System.Drawing.Size(35, 13);
            this.label5.TabIndex = 32;
            this.label5.Text = "Brand";
            // 
            // txt_model
            // 
            this.txt_model.Location = new System.Drawing.Point(197, 48);
            this.txt_model.Name = "txt_model";
            this.txt_model.Size = new System.Drawing.Size(154, 20);
            this.txt_model.TabIndex = 31;
            // 
            // txt_brand
            // 
            this.txt_brand.Location = new System.Drawing.Point(29, 48);
            this.txt_brand.Name = "txt_brand";
            this.txt_brand.Size = new System.Drawing.Size(151, 20);
            this.txt_brand.TabIndex = 30;
            // 
            // label6
            // 
            this.label6.AutoSize = true;
            this.label6.Location = new System.Drawing.Point(105, 122);
            this.label6.Name = "label6";
            this.label6.Size = new System.Drawing.Size(22, 13);
            this.label6.TabIndex = 37;
            this.label6.Text = "HP";
            // 
            // label7
            // 
            this.label7.AutoSize = true;
            this.label7.Location = new System.Drawing.Point(26, 122);
            this.label7.Name = "label7";
            this.label7.Size = new System.Drawing.Size(21, 13);
            this.label7.TabIndex = 36;
            this.label7.Text = "CC";
            // 
            // txt_hp
            // 
            this.txt_hp.Location = new System.Drawing.Point(108, 138);
            this.txt_hp.Name = "txt_hp";
            this.txt_hp.Size = new System.Drawing.Size(72, 20);
            this.txt_hp.TabIndex = 35;
            // 
            // txt_cc
            // 
            this.txt_cc.Location = new System.Drawing.Point(29, 138);
            this.txt_cc.Name = "txt_cc";
            this.txt_cc.Size = new System.Drawing.Size(73, 20);
            this.txt_cc.TabIndex = 34;
            // 
            // groupBox1
            // 
            this.groupBox1.Controls.Add(this.txt_brand);
            this.groupBox1.Controls.Add(this.label6);
            this.groupBox1.Controls.Add(this.txt_frame);
            this.groupBox1.Controls.Add(this.label7);
            this.groupBox1.Controls.Add(this.txt_year);
            this.groupBox1.Controls.Add(this.txt_hp);
            this.groupBox1.Controls.Add(this.label1);
            this.groupBox1.Controls.Add(this.txt_cc);
            this.groupBox1.Controls.Add(this.label2);
            this.groupBox1.Controls.Add(this.label3);
            this.groupBox1.Controls.Add(this.txt_model);
            this.groupBox1.Controls.Add(this.label5);
            this.groupBox1.Location = new System.Drawing.Point(855, 51);
            this.groupBox1.Name = "groupBox1";
            this.groupBox1.Size = new System.Drawing.Size(387, 197);
            this.groupBox1.TabIndex = 38;
            this.groupBox1.TabStop = false;
            this.groupBox1.Text = "Main";
            // 
            // gb_stock
            // 
            this.gb_stock.Controls.Add(this.txt_price);
            this.gb_stock.Controls.Add(this.label9);
            this.gb_stock.Controls.Add(this.lb_stand);
            this.gb_stock.Controls.Add(this.label8);
            this.gb_stock.Location = new System.Drawing.Point(855, 254);
            this.gb_stock.Name = "gb_stock";
            this.gb_stock.Size = new System.Drawing.Size(387, 129);
            this.gb_stock.TabIndex = 39;
            this.gb_stock.TabStop = false;
            this.gb_stock.Text = "Stock";
            // 
            // txt_price
            // 
            this.txt_price.Location = new System.Drawing.Point(197, 49);
            this.txt_price.Name = "txt_price";
            this.txt_price.Size = new System.Drawing.Size(73, 20);
            this.txt_price.TabIndex = 36;
            // 
            // label9
            // 
            this.label9.AutoSize = true;
            this.label9.Location = new System.Drawing.Point(194, 33);
            this.label9.Name = "label9";
            this.label9.Size = new System.Drawing.Size(31, 13);
            this.label9.TabIndex = 38;
            this.label9.Text = "Price";
            // 
            // lb_stand
            // 
            this.lb_stand.FormattingEnabled = true;
            this.lb_stand.Location = new System.Drawing.Point(29, 49);
            this.lb_stand.Name = "lb_stand";
            this.lb_stand.Size = new System.Drawing.Size(151, 69);
            this.lb_stand.TabIndex = 39;
            // 
            // label8
            // 
            this.label8.AutoSize = true;
            this.label8.Location = new System.Drawing.Point(26, 33);
            this.label8.Name = "label8";
            this.label8.Size = new System.Drawing.Size(35, 13);
            this.label8.TabIndex = 38;
            this.label8.Text = "Stand";
            // 
            // gb_rent
            // 
            this.gb_rent.Controls.Add(this.txt_pricekm);
            this.gb_rent.Controls.Add(this.label12);
            this.gb_rent.Controls.Add(this.txt_totalkm);
            this.gb_rent.Controls.Add(this.label10);
            this.gb_rent.Controls.Add(this.lb_motostation);
            this.gb_rent.Controls.Add(this.label11);
            this.gb_rent.Location = new System.Drawing.Point(855, 254);
            this.gb_rent.Name = "gb_rent";
            this.gb_rent.Size = new System.Drawing.Size(387, 129);
            this.gb_rent.TabIndex = 40;
            this.gb_rent.TabStop = false;
            this.gb_rent.Text = "Rent";
            // 
            // txt_pricekm
            // 
            this.txt_pricekm.Location = new System.Drawing.Point(197, 87);
            this.txt_pricekm.Name = "txt_pricekm";
            this.txt_pricekm.Size = new System.Drawing.Size(73, 20);
            this.txt_pricekm.TabIndex = 40;
            // 
            // label12
            // 
            this.label12.AutoSize = true;
            this.label12.Location = new System.Drawing.Point(194, 71);
            this.label12.Name = "label12";
            this.label12.Size = new System.Drawing.Size(64, 13);
            this.label12.TabIndex = 41;
            this.label12.Text = "Price p/ KM";
            // 
            // txt_totalkm
            // 
            this.txt_totalkm.Location = new System.Drawing.Point(197, 49);
            this.txt_totalkm.Name = "txt_totalkm";
            this.txt_totalkm.Size = new System.Drawing.Size(73, 20);
            this.txt_totalkm.TabIndex = 38;
            // 
            // label10
            // 
            this.label10.AutoSize = true;
            this.label10.Location = new System.Drawing.Point(194, 33);
            this.label10.Name = "label10";
            this.label10.Size = new System.Drawing.Size(50, 13);
            this.label10.TabIndex = 38;
            this.label10.Text = "Total KM";
            // 
            // lb_motostation
            // 
            this.lb_motostation.FormattingEnabled = true;
            this.lb_motostation.Location = new System.Drawing.Point(29, 49);
            this.lb_motostation.Name = "lb_motostation";
            this.lb_motostation.Size = new System.Drawing.Size(151, 69);
            this.lb_motostation.TabIndex = 39;
            // 
            // label11
            // 
            this.label11.AutoSize = true;
            this.label11.Location = new System.Drawing.Point(26, 33);
            this.label11.Name = "label11";
            this.label11.Size = new System.Drawing.Size(62, 13);
            this.label11.TabIndex = 38;
            this.label11.Text = "Motostation";
            // 
            // btn_insert
            // 
            this.btn_insert.BackColor = System.Drawing.Color.Transparent;
            this.btn_insert.Location = new System.Drawing.Point(855, 389);
            this.btn_insert.Name = "btn_insert";
            this.btn_insert.Size = new System.Drawing.Size(387, 37);
            this.btn_insert.TabIndex = 40;
            this.btn_insert.Text = "Insert";
            this.btn_insert.UseVisualStyleBackColor = false;
            this.btn_insert.Click += new System.EventHandler(this.btn_insert_Click);
            // 
            // btn_remove
            // 
            this.btn_remove.BackColor = System.Drawing.Color.Transparent;
            this.btn_remove.Location = new System.Drawing.Point(12, 389);
            this.btn_remove.Name = "btn_remove";
            this.btn_remove.Size = new System.Drawing.Size(340, 37);
            this.btn_remove.TabIndex = 41;
            this.btn_remove.Text = "Remove";
            this.btn_remove.UseVisualStyleBackColor = false;
            this.btn_remove.Click += new System.EventHandler(this.btn_remove_Click);
            // 
            // label13
            // 
            this.label13.AutoSize = true;
            this.label13.Font = new System.Drawing.Font("Microsoft Sans Serif", 15.75F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((byte)(0)));
            this.label13.Location = new System.Drawing.Point(403, 12);
            this.label13.Name = "label13";
            this.label13.Size = new System.Drawing.Size(81, 25);
            this.label13.TabIndex = 42;
            this.label13.Text = "Update";
            // 
            // txt_brand_edit
            // 
            this.txt_brand_edit.Enabled = false;
            this.txt_brand_edit.Location = new System.Drawing.Point(408, 76);
            this.txt_brand_edit.Name = "txt_brand_edit";
            this.txt_brand_edit.Size = new System.Drawing.Size(125, 20);
            this.txt_brand_edit.TabIndex = 43;
            // 
            // txt_model_edit
            // 
            this.txt_model_edit.Enabled = false;
            this.txt_model_edit.Location = new System.Drawing.Point(539, 76);
            this.txt_model_edit.Name = "txt_model_edit";
            this.txt_model_edit.Size = new System.Drawing.Size(125, 20);
            this.txt_model_edit.TabIndex = 44;
            // 
            // txt_frame_edit
            // 
            this.txt_frame_edit.Enabled = false;
            this.txt_frame_edit.Location = new System.Drawing.Point(408, 121);
            this.txt_frame_edit.Name = "txt_frame_edit";
            this.txt_frame_edit.Size = new System.Drawing.Size(125, 20);
            this.txt_frame_edit.TabIndex = 45;
            // 
            // txt_year_edit
            // 
            this.txt_year_edit.Enabled = false;
            this.txt_year_edit.Location = new System.Drawing.Point(539, 121);
            this.txt_year_edit.Name = "txt_year_edit";
            this.txt_year_edit.Size = new System.Drawing.Size(125, 20);
            this.txt_year_edit.TabIndex = 46;
            this.txt_year_edit.TextChanged += new System.EventHandler(this.txt_year_edit_TextChanged);
            // 
            // txt_cc_edit
            // 
            this.txt_cc_edit.Enabled = false;
            this.txt_cc_edit.Location = new System.Drawing.Point(670, 76);
            this.txt_cc_edit.Name = "txt_cc_edit";
            this.txt_cc_edit.Size = new System.Drawing.Size(59, 20);
            this.txt_cc_edit.TabIndex = 47;
            // 
            // txt_hp_edit
            // 
            this.txt_hp_edit.Enabled = false;
            this.txt_hp_edit.Location = new System.Drawing.Point(735, 76);
            this.txt_hp_edit.Name = "txt_hp_edit";
            this.txt_hp_edit.Size = new System.Drawing.Size(59, 20);
            this.txt_hp_edit.TabIndex = 48;
            // 
            // button2
            // 
            this.button2.BackColor = System.Drawing.Color.Transparent;
            this.button2.Location = new System.Drawing.Point(408, 232);
            this.button2.Name = "button2";
            this.button2.Size = new System.Drawing.Size(125, 37);
            this.button2.TabIndex = 50;
            this.button2.Text = "Edit";
            this.button2.UseVisualStyleBackColor = false;
            this.button2.Click += new System.EventHandler(this.button2_Click);
            // 
            // label14
            // 
            this.label14.AutoSize = true;
            this.label14.Location = new System.Drawing.Point(536, 60);
            this.label14.Name = "label14";
            this.label14.Size = new System.Drawing.Size(36, 13);
            this.label14.TabIndex = 51;
            this.label14.Text = "Model";
            // 
            // label15
            // 
            this.label15.AutoSize = true;
            this.label15.Location = new System.Drawing.Point(405, 60);
            this.label15.Name = "label15";
            this.label15.Size = new System.Drawing.Size(35, 13);
            this.label15.TabIndex = 52;
            this.label15.Text = "Brand";
            // 
            // label16
            // 
            this.label16.AutoSize = true;
            this.label16.Location = new System.Drawing.Point(405, 105);
            this.label16.Name = "label16";
            this.label16.Size = new System.Drawing.Size(53, 13);
            this.label16.TabIndex = 53;
            this.label16.Text = "Frame No";
            // 
            // label17
            // 
            this.label17.AutoSize = true;
            this.label17.Location = new System.Drawing.Point(536, 105);
            this.label17.Name = "label17";
            this.label17.Size = new System.Drawing.Size(29, 13);
            this.label17.TabIndex = 54;
            this.label17.Text = "Year";
            // 
            // label18
            // 
            this.label18.AutoSize = true;
            this.label18.Location = new System.Drawing.Point(667, 60);
            this.label18.Name = "label18";
            this.label18.Size = new System.Drawing.Size(21, 13);
            this.label18.TabIndex = 55;
            this.label18.Text = "CC";
            // 
            // label19
            // 
            this.label19.AutoSize = true;
            this.label19.Location = new System.Drawing.Point(732, 60);
            this.label19.Name = "label19";
            this.label19.Size = new System.Drawing.Size(22, 13);
            this.label19.TabIndex = 56;
            this.label19.Text = "HP";
            // 
            // txt_price_edit
            // 
            this.txt_price_edit.Enabled = false;
            this.txt_price_edit.Location = new System.Drawing.Point(408, 189);
            this.txt_price_edit.Name = "txt_price_edit";
            this.txt_price_edit.Size = new System.Drawing.Size(125, 20);
            this.txt_price_edit.TabIndex = 57;
            // 
            // lbl_price
            // 
            this.lbl_price.AutoSize = true;
            this.lbl_price.Location = new System.Drawing.Point(405, 173);
            this.lbl_price.Name = "lbl_price";
            this.lbl_price.Size = new System.Drawing.Size(31, 13);
            this.lbl_price.TabIndex = 58;
            this.lbl_price.Text = "Price";
            this.lbl_price.Click += new System.EventHandler(this.label20_Click);
            // 
            // txt_pricekm_edit
            // 
            this.txt_pricekm_edit.Enabled = false;
            this.txt_pricekm_edit.Location = new System.Drawing.Point(408, 189);
            this.txt_pricekm_edit.Name = "txt_pricekm_edit";
            this.txt_pricekm_edit.Size = new System.Drawing.Size(125, 20);
            this.txt_pricekm_edit.TabIndex = 59;
            this.txt_pricekm_edit.Visible = false;
            // 
            // lbl_pricekm
            // 
            this.lbl_pricekm.AutoSize = true;
            this.lbl_pricekm.Location = new System.Drawing.Point(405, 173);
            this.lbl_pricekm.Name = "lbl_pricekm";
            this.lbl_pricekm.Size = new System.Drawing.Size(56, 13);
            this.lbl_pricekm.TabIndex = 60;
            this.lbl_pricekm.Text = "Price / km";
            this.lbl_pricekm.Visible = false;
            // 
            // lbl_totalkm
            // 
            this.lbl_totalkm.AutoSize = true;
            this.lbl_totalkm.Location = new System.Drawing.Point(542, 173);
            this.lbl_totalkm.Name = "lbl_totalkm";
            this.lbl_totalkm.Size = new System.Drawing.Size(48, 13);
            this.lbl_totalkm.TabIndex = 62;
            this.lbl_totalkm.Text = "Total km";
            this.lbl_totalkm.Visible = false;
            // 
            // txt_totalkm_edit
            // 
            this.txt_totalkm_edit.Enabled = false;
            this.txt_totalkm_edit.Location = new System.Drawing.Point(545, 189);
            this.txt_totalkm_edit.Name = "txt_totalkm_edit";
            this.txt_totalkm_edit.Size = new System.Drawing.Size(125, 20);
            this.txt_totalkm_edit.TabIndex = 61;
            this.txt_totalkm_edit.Visible = false;
            // 
            // txt_local_edit
            // 
            this.txt_local_edit.Enabled = false;
            this.txt_local_edit.Location = new System.Drawing.Point(670, 121);
            this.txt_local_edit.Name = "txt_local_edit";
            this.txt_local_edit.Size = new System.Drawing.Size(125, 20);
            this.txt_local_edit.TabIndex = 63;
            // 
            // label20
            // 
            this.label20.AutoSize = true;
            this.label20.Location = new System.Drawing.Point(667, 105);
            this.label20.Name = "label20";
            this.label20.Size = new System.Drawing.Size(63, 13);
            this.label20.TabIndex = 64;
            this.label20.Text = "Localization";
            // 
            // btn_update
            // 
            this.btn_update.BackColor = System.Drawing.Color.Transparent;
            this.btn_update.Location = new System.Drawing.Point(408, 232);
            this.btn_update.Name = "btn_update";
            this.btn_update.Size = new System.Drawing.Size(125, 37);
            this.btn_update.TabIndex = 65;
            this.btn_update.Text = "Update";
            this.btn_update.UseVisualStyleBackColor = false;
            this.btn_update.Visible = false;
            this.btn_update.Click += new System.EventHandler(this.btn_update_Click);
            // 
            // label21
            // 
            this.label21.AutoSize = true;
            this.label21.Font = new System.Drawing.Font("Microsoft Sans Serif", 15.75F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((byte)(0)));
            this.label21.Location = new System.Drawing.Point(7, 12);
            this.label21.Name = "label21";
            this.label21.Size = new System.Drawing.Size(128, 25);
            this.label21.TabIndex = 66;
            this.label21.Text = "Motorcycles";
            // 
            // Motorcycles
            // 
            this.AutoScaleDimensions = new System.Drawing.SizeF(6F, 13F);
            this.AutoScaleMode = System.Windows.Forms.AutoScaleMode.Font;
            this.ClientSize = new System.Drawing.Size(1264, 681);
            this.Controls.Add(this.label21);
            this.Controls.Add(this.btn_update);
            this.Controls.Add(this.label20);
            this.Controls.Add(this.txt_local_edit);
            this.Controls.Add(this.lbl_totalkm);
            this.Controls.Add(this.txt_totalkm_edit);
            this.Controls.Add(this.lbl_pricekm);
            this.Controls.Add(this.txt_pricekm_edit);
            this.Controls.Add(this.lbl_price);
            this.Controls.Add(this.txt_price_edit);
            this.Controls.Add(this.label19);
            this.Controls.Add(this.label18);
            this.Controls.Add(this.label17);
            this.Controls.Add(this.label16);
            this.Controls.Add(this.label15);
            this.Controls.Add(this.label14);
            this.Controls.Add(this.button2);
            this.Controls.Add(this.txt_hp_edit);
            this.Controls.Add(this.txt_cc_edit);
            this.Controls.Add(this.txt_year_edit);
            this.Controls.Add(this.txt_frame_edit);
            this.Controls.Add(this.txt_model_edit);
            this.Controls.Add(this.txt_brand_edit);
            this.Controls.Add(this.label13);
            this.Controls.Add(this.btn_remove);
            this.Controls.Add(this.btn_insert);
            this.Controls.Add(this.gb_stock);
            this.Controls.Add(this.gb_rent);
            this.Controls.Add(this.groupBox1);
            this.Controls.Add(this.label4);
            this.Controls.Add(this.btn_rent);
            this.Controls.Add(this.btn_stock);
            this.Controls.Add(this.rent_grid_view);
            this.Controls.Add(this.stock_grid_view);
            this.Name = "Motorcycles";
            this.Text = "Motorcycle";
            this.Load += new System.EventHandler(this.Motorcycle_Load);
            ((System.ComponentModel.ISupportInitialize)(this.stock_grid_view)).EndInit();
            ((System.ComponentModel.ISupportInitialize)(this.rent_grid_view)).EndInit();
            this.groupBox1.ResumeLayout(false);
            this.groupBox1.PerformLayout();
            this.gb_stock.ResumeLayout(false);
            this.gb_stock.PerformLayout();
            this.gb_rent.ResumeLayout(false);
            this.gb_rent.PerformLayout();
            this.ResumeLayout(false);
            this.PerformLayout();

        }

        #endregion
        private System.Windows.Forms.DataGridView stock_grid_view;
        private System.Windows.Forms.DataGridView rent_grid_view;
        private System.Windows.Forms.Button btn_stock;
        private System.Windows.Forms.Button btn_rent;
        private System.Windows.Forms.Label label4;
        private System.Windows.Forms.Label label2;
        private System.Windows.Forms.Label label1;
        private System.Windows.Forms.TextBox txt_year;
        private System.Windows.Forms.TextBox txt_frame;
        private System.Windows.Forms.Label label3;
        private System.Windows.Forms.Label label5;
        private System.Windows.Forms.TextBox txt_model;
        private System.Windows.Forms.TextBox txt_brand;
        private System.Windows.Forms.Label label6;
        private System.Windows.Forms.Label label7;
        private System.Windows.Forms.TextBox txt_hp;
        private System.Windows.Forms.TextBox txt_cc;
        private System.Windows.Forms.GroupBox groupBox1;
        private System.Windows.Forms.GroupBox gb_stock;
        private System.Windows.Forms.GroupBox gb_rent;
        private System.Windows.Forms.TextBox txt_pricekm;
        private System.Windows.Forms.Label label12;
        private System.Windows.Forms.TextBox txt_totalkm;
        private System.Windows.Forms.Label label10;
        private System.Windows.Forms.ListBox lb_motostation;
        private System.Windows.Forms.Label label11;
        private System.Windows.Forms.TextBox txt_price;
        private System.Windows.Forms.Label label9;
        private System.Windows.Forms.ListBox lb_stand;
        private System.Windows.Forms.Label label8;
        private System.Windows.Forms.Button btn_insert;
        private System.Windows.Forms.Button btn_remove;
        private System.Windows.Forms.Label label13;
        private System.Windows.Forms.TextBox txt_brand_edit;
        private System.Windows.Forms.TextBox txt_model_edit;
        private System.Windows.Forms.TextBox txt_frame_edit;
        private System.Windows.Forms.TextBox txt_year_edit;
        private System.Windows.Forms.TextBox txt_cc_edit;
        private System.Windows.Forms.TextBox txt_hp_edit;
        private System.Windows.Forms.Button button2;
        private System.Windows.Forms.Label label14;
        private System.Windows.Forms.Label label15;
        private System.Windows.Forms.Label label16;
        private System.Windows.Forms.Label label17;
        private System.Windows.Forms.Label label18;
        private System.Windows.Forms.Label label19;
        private System.Windows.Forms.TextBox txt_price_edit;
        private System.Windows.Forms.Label lbl_price;
        private System.Windows.Forms.TextBox txt_pricekm_edit;
        private System.Windows.Forms.Label lbl_pricekm;
        private System.Windows.Forms.Label lbl_totalkm;
        private System.Windows.Forms.TextBox txt_totalkm_edit;
        private TextBox txt_local_edit;
        private Label label20;
        private Button btn_update;
        private Label label21;
    }
}