DBCC FREEPROCCACHE;
DBCC DROPCLEANBUFFERS;

-- ex 101
select * from Production.WorkOrderselect * from Production.WorkOrder where WorkOrderID=1234SELECT * FROM Production.WorkOrder
WHERE WorkOrderID between 10000 and 10010

SELECT * FROM Production.WorkOrder
WHERE WorkOrderID between 1 and 72591

SELECT * FROM Production.WorkOrder
WHERE StartDate = '2007-06-25'CREATE INDEX productidx ON Production.WorkOrder(ProductID)SELECT * FROM Production.WorkOrder WHERE ProductID = 757CREATE INDEX idxproductincludesd ON Production.WorkOrder (ProductID) INCLUDE (StartDate)SELECT WorkOrderID, StartDate FROM Production.WorkOrder
WHERE ProductID = 757SELECT WorkOrderID, StartDate FROM Production.WorkOrder
WHERE ProductID = 945SELECT WorkOrderID FROM Production.WorkOrder
WHERE ProductID = 945 AND StartDate = '2006-01-04'CREATE INDEX IdxStartDate ON Production.WorkOrder (StartDate)SELECT WorkOrderID, StartDate FROM Production.WorkOrder
WHERE ProductID = 945 AND StartDate = '2006-01-04'CREATE INDEX idxsdproduct ON Production.WorkOrder(ProductID, StartDate)