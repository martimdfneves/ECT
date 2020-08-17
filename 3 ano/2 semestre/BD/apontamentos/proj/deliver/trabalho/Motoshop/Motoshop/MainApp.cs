using System;
using System.Collections.Generic;
using System.ComponentModel;
using System.Data;
using System.Data.SqlClient;
using System.Drawing;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using System.Windows.Forms;

namespace Motoshop
{
    public partial class MainApp : Form
    {
        Clients clients;
        Motorcycles moto;
        Staff staff;
        Store store;

        private SqlConnection cn;
        private DatabaseHandler db;

        public MainApp()
        {
            InitializeComponent();
            db = new DatabaseHandler();
            db.initSGBDConnection();
            this.FormBorderStyle = FormBorderStyle.FixedSingle;
            this.MaximizeBox = false;
            this.MinimizeBox = false;

            clients = new Clients();
            clients.TopLevel = false;
            clients.AutoScroll = true;
            clients.Visible = true;
            clients.FormBorderStyle = System.Windows.Forms.FormBorderStyle.None;
            clients.FormClosed += new FormClosedEventHandler(clients_close);

            moto = new Motorcycles();
            moto.TopLevel = false;
            moto.AutoScroll = true;
            moto.Visible = true;
            moto.FormBorderStyle = System.Windows.Forms.FormBorderStyle.None;
            moto.FormClosed += new FormClosedEventHandler(moto_close);

            staff = new Staff();
            staff.TopLevel = false;
            staff.AutoScroll = true;
            staff.Visible = true;
            staff.FormBorderStyle = System.Windows.Forms.FormBorderStyle.None;
            staff.FormClosed += new FormClosedEventHandler(staff_close);

            store = new Store();
            store.TopLevel = false;
            store.AutoScroll = true;
            store.Visible = true;
            store.FormBorderStyle = System.Windows.Forms.FormBorderStyle.None;
            store.FormClosed += new FormClosedEventHandler(store_close);

        }

        private void MainApp_Load(object sender, EventArgs e)
        {

            // Hardcoded value
            Login.logged_in_staff_member = 105;

            cn = db.getSGBDConnection();

            if (!db.verifySGBDConnection())
                return;

            DataSet ds = new DataSet();
            SqlCommand cmd = new SqlCommand("SELECT S_NAME FROM STAFF_INFO(@staff_nr)", cn);
            cmd.Parameters.AddWithValue("staff_nr", Login.logged_in_staff_member);
            cmd.Connection = cn;
            SqlDataReader reader = cmd.ExecuteReader();
            while (reader.Read())
            {
                lbl_logged.Text += " " + reader["S_NAME"].ToString();
            }
            cn.Close();
            this.content.Controls.Add(clients);
            clients.Show();

            this.store_tab_btn.PerformClick();
        }

        private void staff_close(object sender, FormClosedEventArgs e)
        {
            staff = null;
        }

        private void moto_close(object sender, FormClosedEventArgs e)
        {
            moto = null;
        }

        private void clients_close(object sender, FormClosedEventArgs e)
        {
            clients = null;
        }

        private void store_close(object sender, FormClosedEventArgs e)
        {
            store = null;
        }

        private void client_tab_btn_Click(object sender, EventArgs e)
        {
            reset_buttons();
            this.content.Controls.Clear();
            this.content.Controls.Add(clients);
            this.client_tab_btn.UseVisualStyleBackColor = false;
            clients.Show();
        }

        private void bike_tab_btn_Click(object sender, EventArgs e)
        {
            reset_buttons();
            this.content.Controls.Clear();
            this.content.Controls.Add(moto);
            this.bike_tab_btn.UseVisualStyleBackColor = false;
            moto.Show();
            moto.Motorcycle_Load(sender, e);
        }

        private void staff_tab_btn_Click(object sender, EventArgs e)
        {
            reset_buttons();
            this.content.Controls.Clear();
            this.content.Controls.Add(staff);
            this.staff_tab_btn.UseVisualStyleBackColor = false;
            staff.Show();
        }

        private void store_tab_btn_Click(object sender, EventArgs e)
        {
            reset_buttons();
            this.content.Controls.Clear();
            this.content.Controls.Add(store);
            this.store_tab_btn.UseVisualStyleBackColor = false;
            store.Show();
        }

        private void reset_buttons()
        {
            this.client_tab_btn.UseVisualStyleBackColor = true;
            this.staff_tab_btn.UseVisualStyleBackColor = true;
            this.bike_tab_btn.UseVisualStyleBackColor = true;
            this.store_tab_btn.UseVisualStyleBackColor = true;
        }
    }
}
