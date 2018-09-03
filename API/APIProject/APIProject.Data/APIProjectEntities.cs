using APIProject.Model.Models;
using Microsoft.AspNet.Identity.EntityFramework;
using System;
using System.Data.Entity;
using System.Data.Entity.Validation;

namespace APIProject.Data
{
    public class APIProjectEntities : IdentityDbContext<User>
    {
        public APIProjectEntities() : base("APIProjectEntities") { }

        public DbSet<Rating> Ratings { get; set; }
        public DbSet<Order> Orders { get; set; }
        public DbSet<Trip> Trips { get; set; }
        public DbSet<Post> Posts { get; set; }
        public DbSet<Image> Images { get; set; }
        public DbSet<Offer> Offers { get; set; }
        public DbSet<City> Cities { get; set; }
        public DbSet<Country> Countries { get; set; }
        public DbSet<Transaction> Transactions { get; set; }
        public DbSet<Category> Categories { get; set; }
        public DbSet<TrackingOrder> TrackingOrders { get; set; }
        public DbSet<EcommerceWebsite> EcommerceWebsites { get; set; }
        public DbSet<Notification> Notifications { get; set; }
        public static APIProjectEntities Create()
        {
            return new APIProjectEntities();
        }

        public virtual void Commit()
        {
            try
            {

                base.SaveChanges();
            }
            catch (DbEntityValidationException e)
            {
                foreach (var eve in e.EntityValidationErrors)
                {
                    Console.WriteLine("Entity of type \"{0}\" in state \"{1}\" has the following validation errors:",
                        eve.Entry.Entity.GetType().Name, eve.Entry.State);
                    foreach (var ve in eve.ValidationErrors)
                    {
                        Console.WriteLine("- Property: \"{0}\", Error: \"{1}\"",
                            ve.PropertyName, ve.ErrorMessage);
                    }
                }
                throw;
            }
        }

        protected override void OnModelCreating(DbModelBuilder modelBuilder)
        {
            modelBuilder.Entity<City>()
                .HasMany(t => t.ListTripFromCity)
                .WithRequired(t => t.FromCity)
                .HasForeignKey(t => t.FromCityId)
                .WillCascadeOnDelete(false);

            modelBuilder.Entity<City>()
                .HasMany(t => t.ListTripToCity)
                .WithRequired(t => t.ToCity)
                .HasForeignKey(t => t.ToCityId)
                .WillCascadeOnDelete(false);

            modelBuilder.Entity<City>()
                .HasMany(t => t.ListPostDeliveryTo)
                .WithRequired(t => t.DeliveryTo)
                .HasForeignKey(t => t.DeliveryToId)
                .WillCascadeOnDelete(false);

            modelBuilder.Entity<City>()
                .HasMany(t => t.ListPostBuyFrom)
                .WithRequired(t => t.BuyFrom)
                .HasForeignKey(t => t.BuyFromId)
                .WillCascadeOnDelete(false);

            //modelBuilder.Entity<Country>()
            //    .HasMany(t => t.ListCity)
            //    .WithRequired(t => t.Country)
            //    .HasForeignKey(t => t.CountryID)
            //    .WillCascadeOnDelete(false);

            base.OnModelCreating(modelBuilder);

        }
    }
}
