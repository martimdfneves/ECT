using System;
using System.Collections.Generic;
using System.Collections.ObjectModel;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using System.Windows;
using System.Windows.Controls;
using System.Windows.Data;
using System.Windows.Documents;
using System.Windows.Input;
using System.Windows.Media;
using System.Windows.Media.Imaging;
using System.Windows.Navigation;
using System.Windows.Shapes;

using System.Data.SqlClient;

namespace Shopping_Agent
{
    /// <summary>
    /// Interaction logic for Purchase.xaml
    /// </summary>
   

    public class ListProductsCart : ObservableCollection<Produto>
    {
        public ListProductsCart(){
            
            
        }

        public void AddProduct(String nome, String preco, int quantity, String link, String img_source, decimal peso) {
            Produto p = new Produto();
            p.Nome = nome;
            p.Price = preco;
            p.Quantity = quantity;
            p.ImageSource = img_source;
            p.link = link;
            p.Peso = peso;
            Add(p);
        
        }

    }
    public partial class Purchase : Page
    {
        private ListProductsCart list;
        private int selectedIndex;
        private String client_email;
        private SqlConnection cn;
        private DBConnection db;
        
        public Purchase()
        {
            
            InitializeComponent();
            
            db = new DBConnection();
            list = new ListProductsCart();
            
            
            listCart.Visibility = Visibility.Collapsed;
            btnBuy.Visibility = Visibility.Collapsed;
            btnRemove.Visibility = Visibility.Collapsed;
            gridPop.Visibility = Visibility.Collapsed;
        }

        private void btnOrders_Click(object sender, RoutedEventArgs e)
        {
            NavigationService navService = NavigationService.GetNavigationService(this);
            Orders ord_page = new Orders();
            navService.Navigate(ord_page);
        }

        private void btnHome_Click(object sender, RoutedEventArgs e)
        {
            NavigationService navService = NavigationService.GetNavigationService(this);
            Client_Home cl_page = new Client_Home();
            navService.Navigate(cl_page);
        }

        private void btnStorage_Click(object sender, RoutedEventArgs e)
        {
            NavigationService navService = NavigationService.GetNavigationService(this);
            Storage st_page = new Storage();
            navService.Navigate(st_page);
        }

        private void btnPurchase_Click(object sender, RoutedEventArgs e)
        {
            NavigationService navService = NavigationService.GetNavigationService(this);
            Purchase pur_page = new Purchase();
            navService.Navigate(pur_page);
        }

        private void btnAccount_Click(object sender, RoutedEventArgs e)
        {
            NavigationService navService = NavigationService.GetNavigationService(this);
            Account acc_page = new Account();
            navService.Navigate(acc_page);
        }

        private void btnCart_Click(object sender, RoutedEventArgs e)
        {
            

            listCart.ItemsSource = list; 
            
            if (listCart.IsVisible)
            {
                listCart.Visibility = Visibility.Collapsed;
                btnBuy.Visibility = Visibility.Collapsed;
                btnRemove.Visibility = Visibility.Collapsed;
                gridPop.Visibility = Visibility.Collapsed;
            }

            else
            {
                listCart.Visibility = Visibility.Visible;
                btnBuy.Visibility = Visibility.Visible;
                btnRemove.Visibility = Visibility.Visible;
                gridPop.Visibility = Visibility.Visible;
            }
            
        }

        private void btnRemove_Click(object sender, RoutedEventArgs e)
        {
            if (list.Count > 0 && selectedIndex < list.Count)
            {
                list.RemoveAt(selectedIndex);
                MessageBox.Show("Item Deleted from the Cart");
            }
            else { MessageBox.Show("No items to delete, add more to the Cart"); }
        }

       
       
        private void btnBuy_Click(object sender, RoutedEventArgs e)
        {
           
            Order ordem = new Order();
            if (list.Any())
            {
                ordem.Cart = list;
                string track_n = "Not Assigned";
                String date_tmp = DateTime.Now.ToString("yyyy-MM-dd");

                ordem.DataCompra = date_tmp;

                //MessageBox.Show(ordem.Data_Realizaçao);


                ordem.Preco = Calc_Preço(list).ToString();
                ordem.TrackingNumber = track_n;

                NavigationService navService = NavigationService.GetNavigationService(this);
                SetOrderInformation info = new SetOrderInformation(ordem);
                navService.Navigate(info);
            }

            else 
            {
                MessageBox.Show("Can't submit a empty order, please add products");
            }

        
        }
        
        private double Calc_Preço(ListProductsCart lista)
        {
            double total = 0;
            IEnumerator<Produto> iterador = lista.GetEnumerator();
            Produto tmp;
            while (iterador.MoveNext())
            {
                tmp = iterador.Current;
                
                total = total + (double.Parse(tmp.Price ) * tmp.Quantity);

            }
            
            return total;
        }
        private void listCart_SelectionChanged(object sender, SelectionChangedEventArgs e)
        {
            
           selectedIndex = listCart.SelectedIndex;
            
        }

      
        private void btnAdd_Click(object sender, RoutedEventArgs e)
        {
            Int32 quantidade = 0;
            decimal weight;
            if(Int32.TryParse(QuantityBox.Text,out quantidade)){
                

            }
            else
            {
                MessageBox.Show("Introduza um numero na quantidade");

            }
            try
            {
                weight = System.Convert.ToDecimal(WeightBox.Text);
                
            }
            catch (Exception ex)
            {
                MessageBox.Show("Please insert a valid value in weight");
                return;
            }

            
            String ProductName = ProductNameBox.Text;
            String LinkBoxString = LinkBox.Text;

            if (ProductName == null | String.IsNullOrEmpty(ProductName) | ProductName == "Name")
            {
                MessageBox.Show("Please insert a product name");
                return;
            }

            if (ProductName == null | String.IsNullOrEmpty(LinkBoxString) | LinkBoxString == "Buy Link")
            {
                MessageBox.Show("Please insert a product link");
                return;
            }
            else
            {
                list.AddProduct(ProductName, PriceBox.Text, quantidade, LinkBoxString, "product.png", weight);
                MessageBox.Show("Product added to cart, check it");
            }
        }
    }
}
