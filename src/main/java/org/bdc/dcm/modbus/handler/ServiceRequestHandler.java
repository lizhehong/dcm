package org.bdc.dcm.modbus.handler;

import org.bdc.dcm.modbus.requests.ReadFullReadHoldingRegistersRequest;

import com.digitalpetri.modbus.requests.MaskWriteRegisterRequest;
import com.digitalpetri.modbus.requests.ModbusRequest;
import com.digitalpetri.modbus.requests.ReadCoilsRequest;
import com.digitalpetri.modbus.requests.ReadDiscreteInputsRequest;
import com.digitalpetri.modbus.requests.ReadInputRegistersRequest;
import com.digitalpetri.modbus.requests.WriteMultipleCoilsRequest;
import com.digitalpetri.modbus.requests.WriteMultipleRegistersRequest;
import com.digitalpetri.modbus.requests.WriteSingleCoilRequest;
import com.digitalpetri.modbus.requests.WriteSingleRegisterRequest;
import com.digitalpetri.modbus.responses.ModbusResponse;

import io.netty.util.ReferenceCountUtil;

public interface ServiceRequestHandler {

	default void onReadHoldingRegisters(
			ServiceRequest<ReadFullReadHoldingRegistersRequest> service) {

		ReferenceCountUtil.release(service.getRequest());
	}

	default void onReadInputRegisters(ServiceRequest<ReadInputRegistersRequest> service) {

		ReferenceCountUtil.release(service.getRequest());
	}

	default void onReadCoils(ServiceRequest<ReadCoilsRequest> service) {

		ReferenceCountUtil.release(service.getRequest());
	}

	default void onReadDiscreteInputs(ServiceRequest<ReadDiscreteInputsRequest> service) {

		ReferenceCountUtil.release(service.getRequest());
	}

	default void onWriteSingleCoil(ServiceRequest<WriteSingleCoilRequest> service) {

		ReferenceCountUtil.release(service.getRequest());
	}

	default void onWriteSingleRegister(
			ServiceRequest<WriteSingleRegisterRequest> service) {

		ReferenceCountUtil.release(service.getRequest());
	}

	default void onWriteMultipleCoils(ServiceRequest<WriteMultipleCoilsRequest> service) {

		ReferenceCountUtil.release(service.getRequest());
	}

	default void onWriteMultipleRegisters(
			ServiceRequest<WriteMultipleRegistersRequest> service) {

		ReferenceCountUtil.release(service.getRequest());
	}

	default void onMaskWriteRegister(ServiceRequest<MaskWriteRegisterRequest> service) {

		ReferenceCountUtil.release(service.getRequest());
	}

	public static interface ServiceRequest<Request extends ModbusRequest> {

		/**
		 * @return the request to service.
		 */
		Request getRequest();
		
		   /**
         * Send a normal response.
         *
         * @param response the service response
         */
        void sendResponse(ModbusResponse response);


	}

}
