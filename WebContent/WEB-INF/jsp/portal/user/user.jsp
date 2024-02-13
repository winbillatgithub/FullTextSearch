<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<div class="main">
	<div class="formList perInfo">
		<div class="toolBtn">
			<a href="#nogo" class="btn122w">取消</a><a href="#nogo" class="btn122">确定</a>
		</div>
		<h2><strong>身份信息</strong></h2>
		<div class="item">
			<span class="label">真实姓名：</span>
			<div class="item-bd"><input class="inp-txt w230 defaultVal" value="请告诉我们您的真实姓名" /></div>
		</div>
		<div class="item">
			<span class="label">性别：</span>
			<div class="item-bd"><span class="mr20"><input type="radio" /><label>男</label></span><span><input type="radio" /><label>女</label></span></div>
		</div>
		<div class="item">
			<span class="label">出生日期：</span>
			<div class="item-bd">
				<select class="select w70">
					<option>1944</option>
				</select>
				年
				<select class="select w70 ml10">
					<option>04</option>
				</select>
				月
				<select class="select w70 ml10">
					<option>15</option>
				</select>
				日
			</div>
		</div>
		<div class="item">
			<span class="label">身份证件类别：</span>
			<div class="item-bd">
				<select class="select w240">
					<option>请选择您的证件类型</option>
				</select>
			</div>
		</div>
		<div class="item">
			<span class="label">身份证件号码：</span>
			<div class="item-bd"><input class="inp-txt w230 defaultVal" value="请输入您的证件号码" /></div>
		</div>
		<div class="item">
			<span class="label">国籍：</span>
			<div class="item-bd">
				<select class="select w240">
					<option>请选择您的国籍</option>
				</select>
			</div>
		</div>
		<div class="item">
			<span class="label">民族：</span>
			<div class="item-bd">
				<select class="select w240">
					<option>请选择您的民族</option>
				</select>
			</div>
		</div>
		<div class="item">
			<span class="label">婚姻状况：</span>
			<div class="item-bd">
				<select class="select w240">
					<option>已婚</option>
				</select>
			</div>
		</div>
		<div class="item">
			<span class="label">文化程度：</span>
			<div class="item-bd">
				<select class="select w240">
					<option>请选择您的学历</option>
				</select>
			</div>
		</div>
		<h2><strong>联系方式</strong></h2>
		<div class="item">
			<span class="label">本人手机号码：</span>
			<div class="item-bd"><input class="inp-txt w230 defaultVal" value="请输入您的手机号码" /></div>
		</div>
		<div class="item">
			<span class="label">家庭电话：</span>
			<div class="item-bd"><input class="inp-txt w230 defaultVal" value="请输入您的电话号码" /></div>
		</div>
		<div class="item">
			<span class="label">单位电话：</span>
			<div class="item-bd"><input class="inp-txt w230 defaultVal" value="请输入您的电话号码" /></div>
		</div>
		<div class="item">
			<span class="label">电子邮件：</span>
			<div class="item-bd"><input class="inp-txt w230 defaultVal" value="请输入您的电子邮件地址" /></div>
		</div>
		<div class="item">
			<span class="label">联系人姓名：</span>
			<div class="item-bd"><input class="inp-txt w230 defaultVal" value="请输入您的其他联系人姓名" /></div>
		</div>
		<div class="item">
			<span class="label">联系人电话：</span>
			<div class="item-bd"><input class="inp-txt w230 defaultVal" value="请输入您的电话号码" /></div>
		</div>
		<h2><strong>工作情况</strong></h2>
		<div class="item">
			<span class="label">参加工作日期：</span>
			<div class="item-bd">
				<select class="select w70">
					<option>1944</option>
				</select>
				年
				<select class="select w70 ml10">
					<option>04</option>
				</select>
				月
				<select class="select w70 ml10">
					<option>15</option>
				</select>
				日
			</div>
		</div>
		<div class="item">
			<span class="label">职业：</span>
			<div class="item-bd">
				<select class="select w240">
					<option>请选择您的职业</option>
				</select>
			</div>
		</div>
		<div class="item">
			<span class="label">工作单位：</span>
			<div class="item-bd"><input class="inp-txt w230 defaultVal" value="请输入您的工作单位" /></div>
		</div>
		<h2><strong>常驻地址</strong></h2>
		<div class="item">
			<span class="label">常驻类型：</span>
			<div class="item-bd">
				<select class="select w240">
					<option>户籍</option>
				</select>
			</div>
		</div>
		<div class="item">
			<span class="label">常驻地址类别：</span>
			<div class="item-bd">
				<select class="select w240">
					<option>户籍住址</option>
				</select>
			</div>
		</div>
		<div class="item">
			<span class="label">常驻地址类别：</span>
			<div class="item-bd">
				<select class="select w240">
					<option>请选择您的职业</option>
				</select>
			</div>
		</div>
		<div class="item">
			<span class="label">常驻地址：</span>
			<div class="item-bd">
				<div class="mb10">
					<select class="select w120">
						<option>请选择所在省</option>
					</select>
					省/直辖市/自治
					<select class="select w120">
						<option>请选择所在市</option>
					</select>
					市/地区
				</div>
				<div class="mb10">
					<select class="select w120">
						<option>请选择所在县</option>
					</select>
					县/区
					<input type="text" class="inp-txt w190 defaultVal" value="请输入所在乡镇" />
					乡/镇/街道办事处
				</div>
				<div class="mb10">
					<input type="text" class="inp-txt w190 defaultVal" value="请输入所在村" />
					村/路
					<input type="text" class="inp-txt w110 defaultVal" value="请输入门牌号" />
					号
				</div>
			</div>
		</div>
		<div class="item">
			<span class="label">邮政编码：</span>
			<div class="item-bd"><input class="inp-txt w230 defaultVal" value="请输入您的邮政编码" /></div>
		</div>
		<h2><strong>医疗支付情况</strong></h2>
		<div class="item">
			<span class="label">医疗保险类别：</span>
			<div class="item-bd">
				<select class="select w240">
					<option>社会基本医疗保险</option>
				</select>
			</div>
		</div>
		<div class="item">
			<span class="label">医疗费用支付方式：</span>
			<div class="item-bd">
				<select class="select w240">
					<option>医疗保险</option>
				</select>
			</div>
		</div>
	</div>
</div>