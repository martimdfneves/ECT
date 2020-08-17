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
    public partial class Store : Form
    {
        private SqlConnection cn;
        private DatabaseHandler db;

        public Store()
        {
            InitializeComponent();
            db = new DatabaseHandler();
            db.initSGBDConnection();
        }

        private void store_load(object sender, EventArgs e)
        {
            loadSales();
            loadRevisions();
            loadRents();
        }

        private void loadSales()
        {
            cn = db.getSGBDConnection();

            if (!db.verifySGBDConnection())
                return;

            DataSet ds = new DataSet();
            SqlCommand cmd = new SqlCommand("SELECT * FROM vw_SALES", cn);
            var adapter = new SqlDataAdapter(cmd);

            //SqlDataReader reader = cmd.ExecuteReader();
            adapter.Fill(ds);
            this.sales_grid_view.DataSource = ds.Tables[0];
            this.sales_grid_view.ClearSelection();

            this.rem_sale.Enabled = false;

            cmd = new SqlCommand("SELECT * FROM BEST_SALESMAN()", cn);
            SqlDataReader reader = cmd.ExecuteReader();

            while (reader.Read())
            {
                this.best_salesman_tb.Text = reader["Salesman"].ToString();
            }

            reader.Close();

            cmd = new SqlCommand("SELECT * FROM MOST_EXPENSIVE_BIKE()", cn);
            reader = cmd.ExecuteReader();

            while (reader.Read())
            {
                this.expensive_bike_tb.Text = reader["Motorcycle"].ToString();
            }

            cn.Close();
        }

        private void loadRevisions()
        {
            cn = db.getSGBDConnection();

            if (!db.verifySGBDConnection())
                return;

            DataSet ds = new DataSet();
            SqlCommand cmd = new SqlCommand("SELECT * FROM vw_REVISIONS", cn);
            var adapter = new SqlDataAdapter(cmd);

            //SqlDataReader reader = cmd.ExecuteReader();
            adapter.Fill(ds);
            this.revisions_grid_view.DataSource = ds.Tables[0];
            this.revisions_grid_view.ClearSelection();

            this.rem_revision.Enabled = false;

            cmd = new SqlCommand("SELECT * FROM BEST_MECHANIC()", cn);
            SqlDataReader reader = cmd.ExecuteReader();

            while (reader.Read())
            {
                this.best_mechanic_tb.Text = reader["Mechanic"].ToString();
            }

            reader.Close();

            cmd = new SqlCommand("SELECT * FROM AVERAGE_PARTS()", cn);
            reader = cmd.ExecuteReader();

            while (reader.Read())
            {
                String avrg = reader["Average"].ToString() + "        ";
                this.avg_part_tb.Text = avrg.Substring(0, 4);
            }

            reader.Close();

            cn.Close();
        }

        private void loadRents()
        {
            cn = db.getSGBDConnection();

            if (!db.verifySGBDConnection())
                return;

            DataSet ds = new DataSet();
            SqlCommand cmd = new SqlCommand("SELECT * FROM vw_RENTS", cn);
            var adapter = new SqlDataAdapter(cmd);

            //SqlDataReader reader = cmd.ExecuteReader();
            adapter.Fill(ds);
            this.rents_grid_view.DataSource = ds.Tables[0];
            this.rents_grid_view.ClearSelection();

            this.rem_rent.Enabled = false;

            cmd = new SqlCommand("SELECT * FROM MOST_RENTED_BIKE()", cn);
            SqlDataReader reader = cmd.ExecuteReader();

            while (reader.Read())
            {
                this.rented_bike_tb.Text = reader["Motorcycle"].ToString();
            }

            reader.Close();

            cmd = new SqlCommand("SELECT * FROM MOST_ACTIVE_CLIENT()", cn);
            reader = cmd.ExecuteReader();

            while (reader.Read())
            {
                this.active_client_tb.Text = reader["Client"].ToString();
            }

            cn.Close();
        }

        private void sales_grid_view_CellClick(object sender, DataGridViewCellEventArgs e)
        {
            if (e.RowIndex >= 0)
            {
                this.rem_sale.Enabled = true;

                this.sales_grid_view.Rows[e.RowIndex].Selected = true;
                String invoice = this.sales_grid_view.Rows[e.RowIndex].Cells[0].FormattedValue.ToString();

                cn = db.getSGBDConnection();

                if (!db.verifySGBDConnection())
                    return;

                SqlCommand cmd = new SqlCommand("SELECT * FROM SALE_DETAILED(@Invoice)", cn);
                cmd.Parameters.AddWithValue("Invoice", invoice);
                SqlDataReader reader = cmd.ExecuteReader();

                while (reader.Read())
                {
                    this.sale_bike.Text = reader["Motorcycle"].ToString();
                    this.sale_cc.Text = reader["CC"].ToString();
                    this.sale_hp.Text = reader["HP"].ToString();
                    this.sale_client.Text = reader["Client"].ToString();
                    this.sale_seller.Text = reader["Seller"].ToString();
                    this.sale_stand.Text = reader["Stand"].ToString();
                    this.sale_price.Text = reader["Price"].ToString();
                }

                cn.Close();
            }
        }

        private void revisions_grid_view_CellClick(object sender, DataGridViewCellEventArgs e)
        {
            if (e.RowIndex >= 0)
            {
                this.rem_revision.Enabled = true;

                this.revisions_grid_view.Rows[e.RowIndex].Selected = true;
                String number = this.revisions_grid_view.Rows[e.RowIndex].Cells[0].FormattedValue.ToString();

                cn = db.getSGBDConnection();

                if (!db.verifySGBDConnection())
                    return;

                SqlCommand cmd = new SqlCommand("SELECT * FROM REVISION_DETAILED(@Number)", cn);
                cmd.Parameters.AddWithValue("Number", number);
                SqlDataReader reader = cmd.ExecuteReader();

                while (reader.Read())
                {
                    this.rev_bike.Text = reader["Motorcycle"].ToString();
                    this.rev_cc.Text = reader["CC"].ToString();
                    this.rev_hp.Text = reader["HP"].ToString();
                    this.rev_owner.Text = reader["Owner"].ToString();
                    this.rev_mechanic.Text = reader["Mechanic"].ToString();
                    this.rev_workshop.Text = reader["Workshop"].ToString();
                    this.rev_price.Text = reader["Price"].ToString();
                    this.rev_parts.Text = reader["Parts"].ToString();
                }

                cn.Close();
            }
        }

        private void rents_grid_view_CellClick(object sender, DataGridViewCellEventArgs e)
        {
            if (e.RowIndex >= 0)
            {
                this.rem_rent.Enabled = true;

                this.rents_grid_view.Rows[e.RowIndex].Selected = true;
                String date = this.rents_grid_view.Rows[e.RowIndex].Cells[0].FormattedValue.ToString();
                String bike = this.rents_grid_view.Rows[e.RowIndex].Cells[1].FormattedValue.ToString();
                date = date.Replace("-", " ");

                cn = db.getSGBDConnection();

                if (!db.verifySGBDConnection())
                    return;

                SqlCommand cmd = new SqlCommand("SELECT * FROM RENT_DETAILED (@Date, @Frame)", cn);
                cmd.Parameters.AddWithValue("Date", date);
                cmd.Parameters.AddWithValue("Frame", bike);
                SqlDataReader reader = cmd.ExecuteReader();

                while (reader.Read())
                {
                    this.rent_bike.Text = reader["Motorcycle"].ToString();
                    this.rent_cc.Text = reader["CC"].ToString();
                    this.rent_hp.Text = reader["HP"].ToString();
                    this.rent_client.Text = reader["Client"].ToString();
                    this.rent_ms.Text = reader["MotoStation"].ToString();
                    this.rent_price.Text = reader["Price"].ToString();
                }

                cn.Close();
            }
        }

        private void add_sale_Click(object sender, EventArgs e)
        {
            AddSaleRevRent sale = new AddSaleRevRent("Sale");
            sale.FormClosed += new FormClosedEventHandler(add_sales_close);
            sale.ShowDialog();
        }

        private void add_sales_close(object sender, FormClosedEventArgs e)
        {
            String bike = ((AddSaleRevRent)sender).bike;
            String client = ((AddSaleRevRent)sender).client;
            String seller = ((AddSaleRevRent)sender).staff;

            if (bike != "" && client != "" && seller != "")
            {
                cn = db.getSGBDConnection();

                if (!db.verifySGBDConnection())
                    return;

                SqlCommand cmd = new SqlCommand("EXEC ms_PERFORM_SALE @Bike, @Client, @Seller", cn);
                cmd.Parameters.AddWithValue("Bike", bike);
                cmd.Parameters.AddWithValue("Client", client);
                cmd.Parameters.AddWithValue("Seller", seller);

                try
                {
                    cmd.ExecuteNonQuery();
                    loadSales();
                }
                catch (Exception ex)
                {
                    MessageBox.Show(ex.Message);
                }

                cn.Close();
            }
        }

        private void add_revision_Click(object sender, EventArgs e)
        {
            AddSaleRevRent revision = new AddSaleRevRent("Revision");
            revision.FormClosed += new FormClosedEventHandler(add_revisions_close);
            revision.ShowDialog();
        }

        private void add_revisions_close(object sender, FormClosedEventArgs e)
        {
            String bike = ((AddSaleRevRent)sender).bike;
            String mechanic = ((AddSaleRevRent)sender).staff;

            if (bike != "" && mechanic != "")
            {
                cn = db.getSGBDConnection();

                if (!db.verifySGBDConnection())
                    return;

                Random rnd = new Random();

                SqlCommand cmd = new SqlCommand("EXEC ms_PERFORM_REVISION @Bike, @Mechanic, @Price", cn);
                cmd.Parameters.AddWithValue("Bike", bike);
                cmd.Parameters.AddWithValue("Price", rnd.Next(20, 500));
                cmd.Parameters.AddWithValue("Mechanic", mechanic);

                try
                {
                    cmd.ExecuteNonQuery();
                    loadRevisions();
                }
                catch (Exception ex)
                {
                    MessageBox.Show(ex.Message);
                }

                cn.Close();
            }
        }

        private void add_rent_Click(object sender, EventArgs e)
        {
            AddSaleRevRent rent = new AddSaleRevRent("Rent");
            rent.FormClosed += new FormClosedEventHandler(add_rents_close);
            rent.ShowDialog();
        }

        private void add_rents_close(object sender, FormClosedEventArgs e)
        {
            String bike = ((AddSaleRevRent)sender).bike;
            String client = ((AddSaleRevRent)sender).client;

            if (bike != "" && client != "")
            {
                cn = db.getSGBDConnection();

                if (!db.verifySGBDConnection())
                    return;

                SqlCommand cmd = new SqlCommand("EXEC ms_PERFORM_RENT @Bike, @Client", cn);
                cmd.Parameters.AddWithValue("Bike", bike);
                cmd.Parameters.AddWithValue("Client", client);

                try
                {
                    cmd.ExecuteNonQuery();
                    loadRents();
                }
                catch (Exception ex)
                {
                    MessageBox.Show(ex.Message);
                }

                cn.Close();
            }
        }

        private void rem_sale_Click(object sender, EventArgs e)
        {
            String id = this.sales_grid_view.Rows[this.sales_grid_view.CurrentCell.RowIndex].Cells[0].FormattedValue.ToString();

            cn = db.getSGBDConnection();

            if (!db.verifySGBDConnection())
                return;

            SqlCommand cmd = new SqlCommand("EXEC ms_REM_SALE @Invoice", cn);
            cmd.Parameters.AddWithValue("Invoice", id);

            try
            {
                cmd.ExecuteNonQuery();
                loadSales();
            }
            catch (Exception ex)
            {
                MessageBox.Show(ex.Message);
            }

            cn.Close();
        }

        private void rem_revision_Click(object sender, EventArgs e)
        {
            String id = this.revisions_grid_view.Rows[this.revisions_grid_view.CurrentCell.RowIndex].Cells[0].FormattedValue.ToString();

            cn = db.getSGBDConnection();

            if (!db.verifySGBDConnection())
                return;

            SqlCommand cmd = new SqlCommand("EXEC ms_REM_REVISION @Number", cn);
            cmd.Parameters.AddWithValue("Number", id);

            try
            {
                cmd.ExecuteNonQuery();
                loadRevisions();
            }
            catch (Exception ex)
            {
                MessageBox.Show(ex.Message);
            }

            cn.Close();
        }

        private void rem_rent_Click(object sender, EventArgs e)
        {
            this.rents_grid_view.Rows[this.sales_grid_view.CurrentCell.RowIndex].Selected = true;
            String date = this.rents_grid_view.Rows[this.rents_grid_view.CurrentCell.RowIndex].Cells[0].FormattedValue.ToString();
            String bike = this.rents_grid_view.Rows[this.rents_grid_view.CurrentCell.RowIndex].Cells[1].FormattedValue.ToString();
            date = date.Replace("-", " ");

            cn = db.getSGBDConnection();

            if (!db.verifySGBDConnection())
                return;

            SqlCommand cmd = new SqlCommand("EXEC ms_REM_RENT @Bike, @Date", cn);
            cmd.Parameters.AddWithValue("Bike", bike);
            cmd.Parameters.AddWithValue("Date", date);

            try
            {
                cmd.ExecuteNonQuery();
                loadRents();
            }
            catch (Exception ex)
            {
                MessageBox.Show(ex.Message);
            }

            cn.Close();
        }
    }
}
