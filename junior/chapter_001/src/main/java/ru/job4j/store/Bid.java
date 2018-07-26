package ru.job4j.store;

public class Bid implements Comparable<Bid> {

   private final int base = 17;

   private Long id;
   private Long book;
   private BidType type;
   private ActionType action;
   private Double price;
   private Long volume;

   public Bid() {
      id = null;
      book = null;
      volume = null;
      price = null;
      type = null;
      action = null;
   }

   public Bid(Bid bid) {
      id = new Long(bid.getId());
      book = new Long(bid.getBook());
      type = bid.getType();
      action = bid.getAction();
      price = new Double(bid.getPrice());
      volume = new Long(bid.getVolume());
   }

   public Long getId() {
      return id;
   }

   @Override
   public int hashCode() {
      int hash = 0;
      hash += id.hashCode();
      hash += book.hashCode();
      hash += type.hashCode();
      hash += action.hashCode();
      hash += price.hashCode();
      hash += volume.hashCode();

      return 31 * base + hash;

   }

   @Override
   public boolean equals(Object o) {
      Bid bid = (Bid) o;
      return     id == bid.getId()
              && book == bid.getBook()
              && type == bid.getType()
              && action == bid.getAction()
              && price == bid.getPrice()
              && volume == bid.getVolume();
   }

   public void setId(Long id) {
      this.id = id;
   }

   public Long getBook() {
      return book;
   }

   public void setBook(Long book) {
      this.book = book;
   }

   public BidType getType() {
      return type;
   }

   public void setType(BidType type) {
      this.type = type;
   }

   public ActionType getAction() {
      return action;
   }

   public void setAction(ActionType action) {
      this.action = action;
   }

   public Double getPrice() {
      return price;
   }

   public void setPrice(Double price) {
      this.price = price;
   }

   public Long getVolume() {
      return volume;
   }

   public void setVolume(Long volume) {
      this.volume = volume;
   }

   @Override
   public int compareTo(Bid bid) {
       int res = -1;
       if (this.equals(bid)) {
         res = 0;
       } else if (getPrice() > bid.getPrice()) {
         res = 1;
       }
       return res;
   }

   public void copy(Bid bid) {
      id = new Long(bid.getId());
      book = new Long(bid.getBook());
      type = bid.getType();
      action = bid.getAction();
      price = new Double(bid.getPrice());
      volume = new Long(bid.getVolume());
   }
}
