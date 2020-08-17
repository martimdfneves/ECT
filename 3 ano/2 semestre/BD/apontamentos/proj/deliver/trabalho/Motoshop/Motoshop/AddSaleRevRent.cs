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
    public partial class AddSaleRevRent : Form
    {
        private SqlConnection cn;
        private DatabaseHandler db;

        private String service;

        public String bike = "";
        public String client = "";
        public String staff = "";

        public AddSaleRevRent(String service)
        {
            InitializeComponent();
            db = new DatabaseHandler();
            db.initSGBDConnection();
            this.service = service;
            
        }

        private void sale_rev_rent_load(object sender, EventArgs e)
        {
            if (service == "Sale")
            {
                this.title_label.Text = "Register new Sale";
                this.Text = "Sale";

                cn = db.getSGBDConnection();

                if (!db.verifySGBDConnection())
                    return;

                SqlCommand cmd = new SqlCommand("SELECT * FROM STOCK_BIKE_INFO", cn);

                SqlDataReader reader = cmd.ExecuteReader();

                while (reader.Read())
                {
                    this.bike_cb.Items.Add(reader["Motorcycle"].ToString());
                    this.bike_cb.SelectedIndex = 0;
                }
                reader.Close();

                cmd = new SqlCommand("SELECT * FROM CLIENT_INFO", cn);

                reader = cmd.ExecuteReader();

                while (reader.Read())
                {
                    this.client_cb.Items.Add(reader["Client"].ToString());
                    this.client_cb.SelectedIndex = 0;
                }
                reader.Close();

                cmd = new SqlCommand("SELECT * FROM SALESMAN_INFO", cn);

                reader = cmd.ExecuteReader();

                while (reader.Read())
                {
                    this.staff_cb.Items.Add(reader["Salesman"].ToString());
                    this.staff_cb.SelectedIndex = 0;
                }
                reader.Close();
            }
            else if (service == "Revision")
            {
                this.title_label.Text = "Register new Revision";
                this.Text = "Revision";
                this.client_label.Visible = false;
                this.client_cb.Visible = false;
                this.staff_label.Text = "Mechanic";

                this.staff_label.Location = new Point(this.staff_label.Location.X, this.staff_label.Location.Y - 25);
                this.staff_cb.Location = new Point(this.staff_cb.Location.X, this.staff_cb.Location.Y - 25);

                cn = db.getSGBDConnection();

                if (!db.verifySGBDConnection())
                    return;

                SqlCommand cmd = new SqlCommand("SELECT * FROM MOTORCYCLE_INFO", cn);

                SqlDataReader reader = cmd.ExecuteReader();

                while (reader.Read())
                {
                    this.bike_cb.Items.Add(reader["Motorcycle"].ToString());
                    this.bike_cb.SelectedIndex = 0;
                }
                reader.Close();

                cmd = new SqlCommand("SELECT * FROM MECHANIC_INFO", cn);

                reader = cmd.ExecuteReader();

                while (reader.Read())
                {
                    this.staff_cb.Items.Add(reader["Mechanic"].ToString());
                    this.staff_cb.SelectedIndex = 0;
                }
                reader.Close();
            }
            else if (service == "Rent")
            {
                this.title_label.Text = "Register new Rent";
                this.Text = "Rent";
                this.staff_label.Visible = false;
                this.staff_cb.Visible = false;

                this.client_label.Location = new Point(this.client_label.Location.X, this.client_label.Location.Y + 20);
                this.client_cb.Location = new Point(this.client_cb.Location.X, this.client_cb.Location.Y + 20);

                cn = db.getSGBDConnection();

                if (!db.verifySGBDConnection())
                    return;

                SqlCommand cmd = new SqlCommand("SELECT * FROM RENT_BIKE_INFO", cn);

                SqlDataReader reader = cmd.ExecuteReader();

                while (reader.Read())
                {
                    this.bike_cb.Items.Add(reader["Motorcycle"].ToString());
                    this.bike_cb.SelectedIndex = 0;
                }
                reader.Close();

                cmd = new SqlCommand("SELECT * FROM CLIENT_INFO", cn);

                reader = cmd.ExecuteReader();

                while (reader.Read())
                {
                    this.client_cb.Items.Add(reader["Client"].ToString());
                    this.client_cb.SelectedIndex = 0;
                }
                reader.Close();
            }
        }

        private void add_btn_Click(object sender, EventArgs e)
        {
            try
            {
                this.bike = bike_cb.SelectedItem.ToString();
            }
            catch (Exception ex)
            {
                MessageBox.Show("Não existem Motas em Stock");
            }
            if (this.client_label.Visible == true)
            {
                this.client = client_cb.SelectedItem.ToString();
                this.client = client.Substring(client.Length - 9);
            }
            if (this.staff_label.Visible == true)
            {
                this.staff = staff_cb.SelectedItem.ToString();
                this.staff = staff.Substring(staff.Length - 3);
            }
            this.Close();
        }

        private void cancel_btn_Click(object sender, EventArgs e)
        {
            this.bike = "";
            this.client = "";
            this.staff = "";
            this.Close();
        }
    }
}
