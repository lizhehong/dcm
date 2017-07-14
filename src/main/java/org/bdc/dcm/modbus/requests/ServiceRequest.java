package org.bdc.dcm.modbus.requests;

import com.digitalpetri.modbus.ExceptionCode;
import com.digitalpetri.modbus.requests.ModbusRequest;
import com.digitalpetri.modbus.responses.ModbusResponse;

public interface ServiceRequest<Request extends ModbusRequest, Response extends ModbusResponse>  {
	 /**
     * @return the transaction id associated with this request.
     */
    short getTransactionId();

    /**
     * @return the unit/slave id this request is directed to.
     */
    short getUnitId();

    /**
     * @return the request to service.
     */
    Request getRequest();

    /**
     * Send a normal response.
     *
     * @param response the service response
     */
    void sendResponse(Response response);

    /**
     * Send an exception response.
     *
     * @param exceptionCode the {@link ExceptionCode}
     */
    void sendException(ExceptionCode exceptionCode);

}
