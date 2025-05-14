    package org.example.bilabonnement.model.contracts;

    import java.time.LocalDate;

    public class RentalContract {
        private int contractId;
        private LocalDate fromDate;
        private LocalDate toDate;
        private int price;
        private int maxKm;
        private int userId;
        private int carId;
        private int customerId;
        private int advanceId;
        private String customerName;

        public String getCustomerName(){
            return customerName;
        }

        public void setCustomerName(String customerName){
            this.customerName = customerName;
        }

        public int getContractId() {
            return contractId;
        }

        public void setContractId(int contractId) {
            this.contractId = contractId;
        }

        public LocalDate getFromDate() {
            return fromDate;
        }

        public void setFromDate(LocalDate fromDate) {
            this.fromDate = fromDate;
        }

        public LocalDate getToDate() {
            return toDate;
        }

        public void setToDate(LocalDate toDate) {
            this.toDate = toDate;
        }

        public int getPrice() {
            return price;
        }

        public void setPrice(int price) {
            this.price = price;
        }

        public int getMaxKm() {
            return maxKm;
        }

        public void setMaxKm(int maxKm) {
            this.maxKm = maxKm;
        }

        public int getUserId() {
            return userId;
        }

        public void setUserId(int userId) {
            this.userId = userId;
        }

        public int getCarId() {
            return carId;
        }

        public void setCarId(int carId) {
            this.carId = carId;
        }

        public int getCustomerId() {
            return customerId;
        }

        public void setCustomerId(int customerId) {
            this.customerId = customerId;
        }

        public int getAdvanceId() {
            return advanceId;
        }

        public void setAdvanceId(int advanceId) {
            this.advanceId = advanceId;
        }
    }
