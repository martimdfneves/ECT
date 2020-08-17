using System;
using System.Collections;
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
    public partial class Motorcycles : Form
    {

        private SqlConnection cn;
        private DatabaseHandler db;
        private ArrayList stands;
        private ArrayList motostations;
        //variavel de controlo. 0 se tem categoria stock ligada, 1 otherwise
        private int current_motorcycle_category;


        public Motorcycles()
        {
            InitializeComponent();
            stands = new ArrayList();
            motostations = new ArrayList();
            db = new DatabaseHandler();
            db.initSGBDConnection();
            current_motorcycle_category = 0;
        }


        private void loadStands()
        {

            cn = db.getSGBDConnection();

            if (!db.verifySGBDConnection())
                return;

            SqlCommand cmd = new SqlCommand("SELECT * FROM STAND_LOCALIZATION", cn);
            SqlDataReader reader = cmd.ExecuteReader();
            while (reader.Read())
            {
                Localization local;
                local = new Localization(Int32.Parse(reader["NUMBER"].ToString()), reader["LOCALIZATION"].ToString());
                stands.Add(local);
                lb_stand.Items.Add(reader["LOCALIZATION"].ToString());
            }
            cn.Close();

        }

        public void loadMotostations()
        {

            cn = db.getSGBDConnection();

            if (!db.verifySGBDConnection())
                return;

            SqlCommand cmd = new SqlCommand("SELECT * FROM MOTOSTATION_LOCALIZATION", cn);
            SqlDataReader reader = cmd.ExecuteReader();
            while (reader.Read())
            {
                Localization local;
                local = new Localization(Int32.Parse(reader["NUMBER"].ToString()), reader["LOCALIZATION"].ToString());
                motostations.Add(local);
                lb_motostation.Items.Add("[" + reader["NUMBER"] + "]" + " " + reader["LOCALIZATION"].ToString());
            }
            cn.Close();
        }


        public void loadMotorcycles()
        {
            cn = db.getSGBDConnection();

            if (!db.verifySGBDConnection())
                return;

            DataSet ds_stock = new DataSet();
            SqlCommand cmd_stock = new SqlCommand("SELECT Frame_no AS 'Frame No', Motorcycle, Price FROM BIKES_FROM_STAND(-1)", cn);
            var adapter_stock = new SqlDataAdapter(cmd_stock);

            //SqlDataReader reader = cmd.ExecuteReader();
            adapter_stock.Fill(ds_stock);
            stock_grid_view.DataSource = ds_stock.Tables[0];

            DataSet ds_rent = new DataSet();
            SqlCommand cmd_rent = new SqlCommand("SELECT Frame_no AS 'Frame No', Motorcycle, Price_Km AS 'Price Km', Total_Km AS 'Total Km' FROM BIKES_FROM_MOTOSTATION(-1)", cn);
            var adapter_rent = new SqlDataAdapter(cmd_rent);

            adapter_rent.Fill(ds_rent);
            rent_grid_view.DataSource = ds_rent.Tables[0];
    
            cn.Close();
        }

        public void Motorcycle_Load(object sender, EventArgs e)
        {
            clearTables();
            loadMotorcycles();
            loadStands();
            loadMotostations();
            if (stock_grid_view.SelectedCells.Count > 0)
            {
                setStockMotorcycleDetail(stock_grid_view.SelectedCells[0].RowIndex);
            }
            if (stock_grid_view.Rows.Count > 0) stock_grid_view.Rows[0].Selected = true;

        }


        private void clearTables()
        {
            lb_stand.Items.Clear();
            lb_motostation.Items.Clear();
            stands.Clear();
            motostations.Clear();
        }


        private void setStockMotorcycleDetail(int currentRow)
        {
           
            try
            {
                cn = db.getSGBDConnection();

                if (!db.verifySGBDConnection())
                    return;

                SqlCommand cmd_stock = new SqlCommand("SELECT * FROM FULL_STOCK_MOTORCYCLE_INFO(@Frame)", cn);
                cmd_stock.Parameters.Clear();
                cmd_stock.Parameters.AddWithValue("@Frame", (int)stock_grid_view.Rows[currentRow].Cells[0].Value);
                SqlDataReader reader = cmd_stock.ExecuteReader();
                while (reader.Read())
                {
                    txt_frame_edit.Text = reader["FRAME_NO"].ToString();
                    txt_brand_edit.Text = reader["BRAND"].ToString();
                    txt_model_edit.Text = reader["MODEL"].ToString();
                    txt_year_edit.Text = reader["YEAR"].ToString();
                    txt_cc_edit.Text = reader["CC"].ToString();
                    txt_hp_edit.Text = reader["HP"].ToString();
                    txt_price_edit.Text = reader["PRICE"].ToString();
                    txt_local_edit.Text = reader["LOCALIZATION"].ToString();

                }
            }
            catch (Exception ex)
            {
                MessageBox.Show(ex.Message);
            }
            finally
            {
                cn.Close();
            }
        }

        private void setRentMotorcycleDetail(int currentRow)
        {
            try
            {
                cn = db.getSGBDConnection();

                if (!db.verifySGBDConnection())
                    return;

                SqlCommand cmd_stock = new SqlCommand("SELECT * FROM FULL_RENT_MOTORCYCLE_INFO(@Frame)", cn);
                cmd_stock.Parameters.Clear();

                cmd_stock.Parameters.AddWithValue("@Frame", (int)rent_grid_view.Rows[currentRow].Cells[0].Value);

                SqlDataReader reader = cmd_stock.ExecuteReader();
                while (reader.Read())
                {
                    txt_frame_edit.Text = reader["FRAME_NO"].ToString();
                    txt_brand_edit.Text = reader["BRAND"].ToString();
                    txt_model_edit.Text = reader["MODEL"].ToString();
                    txt_year_edit.Text = reader["YEAR"].ToString();
                    txt_cc_edit.Text = reader["CC"].ToString();
                    txt_hp_edit.Text = reader["HP"].ToString();
                    txt_pricekm_edit.Text = reader["PRICE_KM"].ToString();
                    txt_totalkm_edit.Text = reader["TOTAL_KM"].ToString();
                    txt_local_edit.Text = reader["LOCALIZATION"].ToString();

                }
            }
            catch (Exception ex)
            {
                MessageBox.Show(ex.Message);
            }
            finally
            {
                cn.Close();
            }
        }

        void stock_grid_view_CellClick(object sender, DataGridViewCellEventArgs e)
        {
            if (e.RowIndex >= 0 && e.RowIndex < stock_grid_view.Rows.Count)
            {
                stock_grid_view.Rows[e.RowIndex].Selected = true;
                setStockMotorcycleDetail(e.RowIndex);
            }

        }

        void rent_grid_view_CellClick(object sender, DataGridViewCellEventArgs e)
        {
            if (e.RowIndex >= 0 && e.RowIndex < rent_grid_view.Rows.Count)
            {
                rent_grid_view.Rows[e.RowIndex].Selected = true;
                setRentMotorcycleDetail(e.RowIndex);
            }
        }

        private void btn_rent_Click(object sender, EventArgs e)
        {
            stock_grid_view.Visible = false;
            rent_grid_view.Visible  = true;
            gb_stock.Hide();
            gb_rent.Show();
            btn_rent.BackColor = Color.Silver;
            btn_stock.BackColor = Color.Transparent;
            current_motorcycle_category = 1;
            lbl_pricekm.Visible = true;
            lbl_totalkm.Visible = true;
            lbl_price.Visible = false;
            txt_price_edit.Visible = false;
            txt_totalkm_edit.Visible = true;
            txt_pricekm_edit.Visible = true;
            clearEditFields();

            if (rent_grid_view.SelectedCells.Count > 0)
            {
                rent_grid_view.Rows[0].Selected = true;
                setRentMotorcycleDetail(rent_grid_view.SelectedCells[0].RowIndex);
            }

        }

        private void btn_stock_Click(object sender, EventArgs e)
        {
            stock_grid_view.Visible = true;
            rent_grid_view.Visible  = false;
            gb_stock.Show();
            gb_rent.Hide();
            btn_rent.BackColor = Color.Transparent;
            btn_stock.BackColor = Color.Silver;
            current_motorcycle_category = 0;
            lbl_pricekm.Visible = false;
            lbl_totalkm.Visible = false;
            lbl_price.Visible = true;
            txt_price_edit.Visible = true;
            txt_totalkm_edit.Visible = false;
            txt_pricekm_edit.Visible = false;
            clearEditFields();

            if (stock_grid_view.SelectedCells.Count > 0)
            {
                stock_grid_view.Rows[0].Selected = true;
                setStockMotorcycleDetail(stock_grid_view.SelectedCells[0].RowIndex);
            }


        }

        private void btn_insert_Click(object sender, EventArgs e)
        {
            addMotorcycle();
            loadMotorcycles();
            clearFields();
        }

        private void clearFields()
        {
            txt_brand.Text = "";
            txt_cc.Text = "";
            txt_frame.Text = "";
            txt_hp.Text = "";
            txt_model.Text = "";
            txt_price.Text = "";
            txt_pricekm.Text = "";
            txt_totalkm.Text = "";
            txt_year.Text = "";
        }

        private void clearEditFields()
        {
            txt_brand_edit.Text = "";
            txt_cc_edit.Text = "";
            txt_frame_edit.Text = "";
            txt_hp_edit.Text = "";
            txt_model_edit.Text = "";
            txt_price_edit.Text = "";
            txt_pricekm_edit.Text = "";
            txt_totalkm_edit.Text = "";
            txt_year_edit.Text = "";
            txt_local_edit.Text = "";
        }


        private void addMotorcycle()
        {
            cn = db.getSGBDConnection();

            if (!db.verifySGBDConnection())
                return;
            SqlCommand cmd = new SqlCommand();
            
            // aba stock ligada, adicionar em stock
            if (current_motorcycle_category == 0)
            {
                cmd.CommandText = "EXEC ms_INSERT_STOCK_MOTORCYCLE @frame, @brand, @model, @year, @cc, @hp, @price, @stand";
                cmd.Parameters.Clear();
                cmd.Parameters.AddWithValue("@frame", Int32.Parse(txt_frame.Text));
                cmd.Parameters.AddWithValue("@brand", txt_brand.Text);
                cmd.Parameters.AddWithValue("@model", txt_model.Text);
                cmd.Parameters.AddWithValue("@year",  Int32.Parse(txt_year.Text));
                cmd.Parameters.AddWithValue("@cc",    Int32.Parse(txt_cc.Text));
                cmd.Parameters.AddWithValue("@hp", Int32.Parse(txt_hp.Text));
                cmd.Parameters.AddWithValue("@price", Int32.Parse(txt_price.Text));
                Localization l = (Localization)stands[lb_stand.SelectedIndex];
                cmd.Parameters.AddWithValue("@stand", l.number);
            }
            else
            {
                cmd.CommandText = "EXEC ms_INSERT_RENTABLE_MOTORCYCLE @frame, @brand, @model, @year, @cc, @hp, @price_km, @total_km, @ms";
                cmd.Parameters.Clear();
                cmd.Parameters.AddWithValue("@frame", Int32.Parse(txt_frame.Text));
                cmd.Parameters.AddWithValue("@brand", txt_brand.Text);
                cmd.Parameters.AddWithValue("@model", txt_model.Text);
                cmd.Parameters.AddWithValue("@year", Int32.Parse(txt_year.Text));
                cmd.Parameters.AddWithValue("@cc", Int32.Parse(txt_cc.Text));
                cmd.Parameters.AddWithValue("@hp", Int32.Parse(txt_hp.Text));
                cmd.Parameters.AddWithValue("@price_km", Int32.Parse(txt_pricekm.Text));
                cmd.Parameters.AddWithValue("@total_km", Int32.Parse(txt_totalkm.Text));
                Localization l = (Localization)motostations[lb_motostation.SelectedIndex];
                cmd.Parameters.AddWithValue("@ms", l.number);
            }

            cmd.Connection = cn;

            try
            {
                cmd.ExecuteNonQuery();
            }
            catch (Exception ex)
            {
                MessageBox.Show(ex.Message);
                //throw new Exception("Failed to update contact in database. \n ERROR MESSAGE: \n" + ex.Message);
            }
        }

       
        private void removeMotorcycle(int motorcycle_frame_no)
        {
            cn = db.getSGBDConnection();

            if (!db.verifySGBDConnection())
                return;
            SqlCommand cmd = new SqlCommand();
            try
            {
                cmd.CommandText = "DELETE MOTORCYCLE WHERE FRAME_NO=@frame";
                cmd.Parameters.Clear();
                cmd.Parameters.AddWithValue("@frame", motorcycle_frame_no);
                cmd.Connection = cn;

            
                cmd.ExecuteNonQuery();
            }
            catch (Exception ex)
            {
                //throw new Exception("Failed to delete contact in database. \n ERROR MESSAGE: \n" + ex.Message);
                MessageBox.Show(ex.Message);
            }
            finally
            {
                cn.Close();
            }
        }

        private void btn_remove_Click(object sender, EventArgs e)
        {
            if (rent_grid_view.SelectedCells.Count > 0 || stock_grid_view.SelectedCells.Count > 0)
            {
                int removeIdx;
                DataGridViewRow selectedRow;
                try
                {
                    if (current_motorcycle_category == 0)
                    {
                        removeIdx = stock_grid_view.SelectedCells[0].RowIndex;
                        selectedRow = stock_grid_view.Rows[removeIdx];
                    }
                    else
                    {
                        removeIdx = rent_grid_view.SelectedCells[0].RowIndex;
                        selectedRow = rent_grid_view.Rows[removeIdx];
                    }
                    removeMotorcycle(Convert.ToInt32(selectedRow.Cells[0].Value));
                    loadMotorcycles();

                }
                catch (Exception ex)
                {
                    MessageBox.Show(ex.Message);
                    return;
                }
            }
        }

        private void label20_Click(object sender, EventArgs e)
        {


        }

        private void txt_year_edit_TextChanged(object sender, EventArgs e)
        {


        }

        private void button2_Click(object sender, EventArgs e)
        {
            button2.Visible = false;
            btn_update.Visible = true;
            if(current_motorcycle_category == 0)
            {
                txt_price_edit.Enabled = true;
            }
            else
            {
                txt_pricekm_edit.Enabled = true;
                txt_totalkm_edit.Enabled = true;
            }
        }

        private void btn_update_Click(object sender, EventArgs e)
        {
            cn = db.getSGBDConnection();

            if (!db.verifySGBDConnection())
                return;

            SqlCommand cmd = new SqlCommand();
            if (current_motorcycle_category == 0)
            {
                cmd.CommandText = "EXEC ms_UPDATE_STOCK_MOTORCYCLE @frame, @price";
                cmd.Parameters.Clear();
                cmd.Parameters.AddWithValue("@frame", Int32.Parse(txt_frame_edit.Text));
                cmd.Parameters.AddWithValue("@price", Int32.Parse(txt_price_edit.Text));
                txt_price_edit.Enabled = false;
                
            }
            else
            {
               
                cmd.CommandText = "EXEC ms_UPDATE_RENT_MOTORCYCLE @frame, @price, @km";
                cmd.Parameters.Clear();
                cmd.Parameters.AddWithValue("@frame", Int32.Parse(txt_frame_edit.Text));
                cmd.Parameters.AddWithValue("@price", Int32.Parse(txt_pricekm_edit.Text));
                cmd.Parameters.AddWithValue("@km", Int32.Parse(txt_totalkm_edit.Text));
                txt_pricekm_edit.Enabled = false;
                txt_totalkm_edit.Enabled = false;
                
            }
            cmd.Connection = cn;

            try { 
            
                cmd.ExecuteNonQuery();
                loadMotorcycles();
                button2.Visible = true;
                btn_update.Visible = false;
            }
            catch(Exception ex)
            {
                MessageBox.Show(ex.Message);
            }

        }
    }
}
