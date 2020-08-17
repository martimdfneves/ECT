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
    public partial class Clients : Form
    {

        private SqlConnection cn;
        private DatabaseHandler db;
        private int currentListIndex;

        public Clients()
        {
            InitializeComponent();
            db = new DatabaseHandler();
            db.initSGBDConnection();
        }


        private void Clients_Load(object sender, EventArgs e)
        {
            loadClients();
            if (client_grid_view.Rows.Count > 0)
            {
                client_grid_view.Rows[0].Selected = true;
                loadClientMotorcycles();
                loadClientsRevisions();
                loadClientRents();
                if (this.client_revisions_grid.Rows.Count > 0)
                {
                    getRevisionParts(0);
                }
            }

            if(client_motorcycles_grid.Rows.Count > 0)
            {
                client_motorcycles_grid.Rows[0].Selected = true;
            }
            if (client_revisions_grid.Rows.Count > 0)
            {
                client_revisions_grid.Rows[0].Selected = true;
            }
            if (client_rents_grid.Rows.Count > 0)
            {
                client_rents_grid.Rows[0].Selected = true;
            }

            //lbl_loggedas.Text += Convert.ToString(Login.logged_in_staff_member);

        }
        void client_grid_view_CellClick(object sender, DataGridViewCellEventArgs e)
        {

            if (e.RowIndex >= 0 && e.RowIndex < client_grid_view.Rows.Count)
            {
                client_grid_view.Rows[e.RowIndex].Selected = true;
                clearTables();
                loadClientMotorcycles();
                loadClientRents();
                loadClientsRevisions();
                if (client_revisions_grid.Rows.Count > 0)
                {
                    client_revisions_grid.Rows[0].Selected = true;
                    getRevisionParts(0);
                }

                if (client_motorcycles_grid.Rows.Count > 0)
                {
                    client_motorcycles_grid.Rows[0].Selected = true;
                }
                if (client_rents_grid.Rows.Count > 0)
                {
                    client_rents_grid.Rows[0].Selected = true;
                }

            }
        }

        private void clearTables()
        {

            changed_parts_grid.DataSource = null;
            changed_parts_grid.Rows.Clear();
            client_revisions_grid.DataSource = null;
            client_revisions_grid.Rows.Clear();
            client_rents_grid.DataSource = null;
            client_rents_grid.Rows.Clear();
            client_motorcycles_grid.DataSource = null;
            client_motorcycles_grid.Rows.Clear();
        }
        

        private void loadClients()
        {
            cn = db.getSGBDConnection();

            if (!db.verifySGBDConnection())
                return;

            DataSet ds = new DataSet();
            SqlCommand cmd = new SqlCommand("SELECT * FROM CLIENT", cn);
            var adapter = new SqlDataAdapter(cmd);
            
            //SqlDataReader reader = cmd.ExecuteReader();
            adapter.Fill(ds);
            client_grid_view.DataSource = ds.Tables[0];
            client_grid_view.Columns[0].HeaderText = "NIF";
            client_grid_view.Columns[1].HeaderText = "NAME";
            client_grid_view.Columns[2].HeaderText = "ADDRESS";
           
            cn.Close();

        }

        private void add_Click(object sender, EventArgs e)
        {
            if(tb_nif.Text == "" || tb_name.Text == "" || tb_addr.Text == "")
            {
                MessageBox.Show("Please insert all values first");
            }
            Client c = new Client(tb_nif.Text, tb_name.Text, tb_addr.Text);
            tb_nif.Text = "";
            tb_name.Text = "";
            tb_addr.Text = "";
            addClient(c);
            loadClients();
        }

        private void addClient(Client client)
        {
            if (!db.verifySGBDConnection())
                return;
            SqlCommand cmd = new SqlCommand();

            cmd.CommandText = "INSERT CLIENT (NIF, C_NAME, C_ADDRESS)" + "VALUES (@NIF, @NAME, @ADDRESS) ";
            cmd.Parameters.Clear();
            cmd.Parameters.AddWithValue("@NIF", Int32.Parse(client.NIF));
            cmd.Parameters.AddWithValue("@NAME", client.Name);
            cmd.Parameters.AddWithValue("@ADDRESS", client.Address);
            cmd.Connection = cn;

            try
            {
                cmd.ExecuteNonQuery();
            }
            catch (Exception ex)
            {
                throw new Exception("Failed to update contact in database. \n ERROR MESSAGE: \n" + ex.Message);
            }
            finally
            {
                cn.Close();
            }
        }

        private void removeClient(string nif)
        {
            if (!db.verifySGBDConnection())
                return;
            SqlCommand cmd = new SqlCommand();

            cmd.CommandText = "DELETE CLIENT WHERE NIF=@nif";
            cmd.Parameters.Clear();
            cmd.Parameters.AddWithValue("@nif", Int32.Parse(nif));
            cmd.Connection = cn;

            try
            {
                cmd.ExecuteNonQuery();
            }
            catch (Exception ex)
            {
                throw new Exception("Failed to delete contact in database. \n ERROR MESSAGE: \n" + ex.Message);
            }
            finally
            {
                cn.Close();
            }
        }

        private void rem_Click(object sender, EventArgs e)
        {
            if (client_grid_view.SelectedCells.Count > 0) {
                int removeIdx;
                try
                {
                    removeIdx = client_grid_view.SelectedCells[0].RowIndex;
                    DataGridViewRow selectedRow = client_grid_view.Rows[removeIdx];
                    removeClient(Convert.ToString(selectedRow.Cells[0].Value));
                    client_grid_view.Rows.RemoveAt(removeIdx);
                }
                catch (Exception ex)
                {
                    MessageBox.Show(ex.Message);
                    return;
                }
            }
        }

        private void client_grid_view_CellContentClick(object sender, DataGridViewCellEventArgs e) { }

        void client_revisions_grid_CellClick(object sender, DataGridViewCellEventArgs e)
        {
            if (e.RowIndex >= 0 && e.RowIndex < client_revisions_grid.Rows.Count)
            {
                client_revisions_grid.Rows[e.RowIndex].Selected = true;
                getRevisionParts(e.RowIndex);
            }
        }

        void client_motorcycles_grid_CellClick(object sender, DataGridViewCellEventArgs e)
        {
            if (e.RowIndex >= 0 && e.RowIndex < client_motorcycles_grid.Rows.Count)
            {
                client_motorcycles_grid.Rows[e.RowIndex].Selected = true;
            }
        }

        void client_rents_grid_CellClick(object sender, DataGridViewCellEventArgs e)
        {
            if (e.RowIndex >= 0 && e.RowIndex < client_rents_grid.Rows.Count)
            {
                client_rents_grid.Rows[e.RowIndex].Selected = true;
            }
        }



        private void loadClientMotorcycles()
        {
            cn = db.getSGBDConnection();

            if (!db.verifySGBDConnection())
                return;

            DataSet ds = new DataSet();
            SqlCommand cmd = new SqlCommand("SELECT * FROM CLIENTS_BIKES(@client)", cn);
            cmd.Parameters.Clear();
            cmd.Parameters.AddWithValue("@client", (int)client_grid_view.SelectedRows[0].Cells[0].Value);
            cmd.Connection = cn;
            var adapter = new SqlDataAdapter(cmd);

            //SqlDataReader reader = cmd.ExecuteReader();
            adapter.Fill(ds);
            client_motorcycles_grid.DataSource = ds.Tables[0];

            cn.Close();
        }

        private void loadClientRents()
        {
            cn = db.getSGBDConnection();

            if (!db.verifySGBDConnection())
                return;

            DataSet ds = new DataSet();
            SqlCommand cmd = new SqlCommand("SELECT * FROM CLIENTS_RENTS(@client)", cn);
            cmd.Parameters.Clear();
            cmd.Parameters.AddWithValue("@client", (int)client_grid_view.SelectedRows[0].Cells[0].Value);
            var adapter = new SqlDataAdapter(cmd);

            //SqlDataReader reader = cmd.ExecuteReader();
            adapter.Fill(ds);
            client_rents_grid.DataSource = ds.Tables[0];

            cn.Close();
        }

        private void loadClientsRevisions()
        {
            cn = db.getSGBDConnection();

            if (!db.verifySGBDConnection())
                return;

            DataSet ds = new DataSet();
            SqlCommand cmd = new SqlCommand("SELECT * FROM CLIENTS_REVISIONS(@client)", cn);
            cmd.Parameters.Clear();
            cmd.Parameters.AddWithValue("@client", (int)client_grid_view.SelectedRows[0].Cells[0].Value);
            var adapter = new SqlDataAdapter(cmd);

            //SqlDataReader reader = cmd.ExecuteReader();
            adapter.Fill(ds);
            client_revisions_grid.DataSource = ds.Tables[0];

            cn.Close();
        }

        private void getRevisionParts(int currentRow)
        {
            cn = db.getSGBDConnection();

            if (!db.verifySGBDConnection())
                return;

            DataSet ds = new DataSet();
            SqlCommand cmd = new SqlCommand("SELECT * FROM REVISION_PARTS(@revision_number)", cn);
            cmd.Parameters.Clear();
            cmd.Parameters.AddWithValue("@revision_number", (int)client_revisions_grid.Rows[currentRow].Cells[0].Value);
            var adapter = new SqlDataAdapter(cmd);

            //SqlDataReader reader = cmd.ExecuteReader();
            adapter.Fill(ds);
            changed_parts_grid.DataSource = ds.Tables[0];

            cn.Close();
        }
    }
}
