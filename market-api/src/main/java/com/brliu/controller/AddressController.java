package com.brliu.controller;

import com.brliu.domain.bo.AddressBO;
import com.brliu.enums.ResponseStateEnum;
import com.brliu.exception.RestMessageException;
import com.brliu.service.interfaces.AddressService;
import com.brliu.utils.MobileEmailUtils;
import com.brliu.utils.ResponseMessage;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@Api(value = "地址相关", tags = {"地址相关的api接口"})
@RequestMapping("address")
@RestController
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class AddressController {

    /**
     * 用户在确认订单页面，可以针对收货地址做如下操作：
     * 1. 查询用户的所有收货地址列表
     * 2. 新增收货地址
     * 3. 删除收货地址
     * 4. 修改收货地址
     * 5. 设置默认地址
     */

    private final AddressService addressService;

    @ApiOperation(value = "根据用户id查询收货地址列表", notes = "根据用户id查询收货地址列表", httpMethod = "POST")
    @PostMapping("/list")
    public void list(@RequestParam String userId) {

        if (StringUtils.isBlank(userId)) {
            throw new RestMessageException(ResponseStateEnum.PARAM_ERROR, "参数不能为空");
        }

        addressService.queryAll(userId);
    }

    @ApiOperation(value = "用户新增地址", notes = "用户新增地址", httpMethod = "POST")
    @PostMapping("/add")
    public ResponseMessage add(@RequestBody AddressBO addressBO) {

        ResponseMessage checkRes = checkAddress(addressBO);
        if (checkRes.getState() != 200) {
            return checkRes;
        }
        addressService.addNewUserAddress(addressBO);
        return ResponseMessage.success();
    }


    private ResponseMessage checkAddress(AddressBO addressBO) {
        String receiver = addressBO.getReceiver();
        if (StringUtils.isBlank(receiver)) {
            return ResponseMessage.paramError("收货人不能为空");
        }
        if (receiver.length() > 12) {
            return ResponseMessage.paramError("收货人姓名不能太长");
        }

        String mobile = addressBO.getMobile();
        if (StringUtils.isBlank(mobile)) {
            return ResponseMessage.paramError("收货人手机号不能为空");
        }
        if (mobile.length() != 11) {
            return ResponseMessage.paramError("收货人手机号长度不正确");
        }
        boolean isMobileOk = MobileEmailUtils.checkMobileIsOk(mobile);
        if (!isMobileOk) {
            return ResponseMessage.paramError("收货人手机号格式不正确");
        }

        String province = addressBO.getProvince();
        String city = addressBO.getCity();
        String district = addressBO.getDistrict();
        String detail = addressBO.getDetail();
        if (StringUtils.isBlank(province) ||
                StringUtils.isBlank(city) ||
                StringUtils.isBlank(district) ||
                StringUtils.isBlank(detail)) {
            return ResponseMessage.paramError("收货地址信息不能为空");
        }

        return ResponseMessage.success();
    }

    @ApiOperation(value = "用户修改地址", notes = "用户修改地址", httpMethod = "POST")
    @PostMapping("/update")
    public ResponseMessage update(@RequestBody AddressBO addressBO) {

        if (StringUtils.isBlank(addressBO.getAddressId())) {
            return ResponseMessage.paramError("修改地址错误：addressId不能为空");
        }

        ResponseMessage checkRes = checkAddress(addressBO);
        if (checkRes.getState() != 200) {
            return checkRes;
        }

        addressService.updateUserAddress(addressBO);

        return ResponseMessage.success();
    }

    @ApiOperation(value = "用户删除地址", notes = "用户删除地址", httpMethod = "POST")
    @PostMapping("/delete")
    public void delete(@RequestParam String userId,
                       @RequestParam String addressId) {

        if (StringUtils.isBlank(userId) || StringUtils.isBlank(addressId)) {
            throw new RestMessageException(ResponseStateEnum.PARAM_ERROR, "输入参数不能为空");
        }
        addressService.deleteUserAddress(userId, addressId);
    }

    @ApiOperation(value = "用户设置默认地址", notes = "用户设置默认地址", httpMethod = "POST")
    @PostMapping("/setDefault")
    public void setDefault(@RequestParam String userId,
                                      @RequestParam String addressId) {

        if (StringUtils.isBlank(userId) || StringUtils.isBlank(addressId)) {
            throw new RestMessageException(ResponseStateEnum.PARAM_ERROR, "输入参数不能为空");
        }
        addressService.updateUserAddressToBeDefault(userId, addressId);
    }
}
